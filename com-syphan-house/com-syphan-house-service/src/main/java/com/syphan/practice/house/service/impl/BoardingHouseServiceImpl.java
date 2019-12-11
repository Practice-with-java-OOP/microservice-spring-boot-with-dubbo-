package com.syphan.practice.house.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syphan.practice.common.api.enumclass.ErrType;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.common.service.base.BaseServiceImpl;
import com.syphan.practice.house.api.BoardingHouseService;
import com.syphan.practice.house.api.dto.BoardingHouseCreateDto;
import com.syphan.practice.house.api.dto.HousePrintDto;
import com.syphan.practice.house.api.dto.UploadResultDto;
import com.syphan.practice.house.api.model.BoardingHouse;
import com.syphan.practice.house.dao.BoardingHouseRepository;
import com.syphan.practice.house.service.utils.Constants;
import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.model.User;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
@org.apache.dubbo.config.annotation.Service
public class BoardingHouseServiceImpl extends BaseServiceImpl<BoardingHouse, Integer> implements BoardingHouseService {

    private Logger logger = LoggerFactory.getLogger(BoardingHouseServiceImpl.class);

    private BoardingHouseRepository repository;

    @Autowired
    public BoardingHouseServiceImpl(BoardingHouseRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Reference
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BoardingHouse create(int userId, BoardingHouseCreateDto data) throws BIZException {
        User user = userService.getById(userId);
        if (user == null) throw BIZException.buildBIZException(ErrType.NOT_FOUND,
                "user.not.found", String.format("%s%s%s", "User with id [ ", userId, " ] not fount"));
        BoardingHouse house = new BoardingHouse();
        house.setUserId(userId);
        house.setUsername(user.getFullName() != null ? user.getFullName() : user.getUsername());
        house.setUserPhone(user.getPhoneNum());
        house.setHouseName(data.getName());
        house.setAddress(data.getAddress());
        house.setRoomNum(data.getRoomNum());
        return repository.save(house);
    }

    @Override
    public String printHouse(Integer id) throws BIZException {
        try {
            Optional<BoardingHouse> boardingHouse = repository.findById(id);
            HousePrintDto housePrintDto = new HousePrintDto();
            if (boardingHouse.isPresent()) {
                BeanUtils.copyProperties(housePrintDto, boardingHouse.get());
            }

            //sau khi di qua JasperReportsInitialization se tao ra cac file tuong ung co duoi .jasper
            //va cac doi tuong se duoc map vao cac filte
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(
                    String.format("%s%s", Constants.HOUSE_TPL_ORDER, Constants.JASPER_EXTENSION)
            );

            logger.debug("Print order: {}", new ObjectMapper().writeValueAsString(housePrintDto));
            JRDataSource jsonDataSource = new JsonDataSource(new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(housePrintDto)));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jsonDataSource);
            byte[] exportReportToPdf = JasperExportManager.exportReportToPdf(jasperPrint);

            final UploadResultDto uploadFile = postUpload(
                    exportReportToPdf, "house_" + id + String.format("%04d", new Random(System.currentTimeMillis()).nextInt(10000)) + ".pdf"
            );

            if (uploadFile == null || uploadFile.getStatus() == null
                    || !uploadFile.getStatus().equalsIgnoreCase("UPLOADED")
                    || uploadFile.getFilePath() == null || uploadFile.getFilePath().isEmpty()) {
                throw BIZException.buildBIZException(ErrType.CONSTRAINT, "", "");
            }
            return "uploadConfigDto.getServerUrl()" + uploadFile.getFilePath();
        } catch (JRException | JsonProcessingException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw BIZException.buildBIZException(ErrType.CONSTRAINT, "", "");
        }
    }

    private UploadResultDto postUpload(final byte[] exportReportToPdf, final String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        byte[] fileBinary = new byte[exportReportToPdf.length];
        System.arraycopy(exportReportToPdf, 0, fileBinary, 0, exportReportToPdf.length);
        body.add("partIndex", 1);
        body.add("fileSize", fileBinary.length);
        body.add("totalFilePart", 1);
        body.add("featureAlias", "PRODUCT_MANAGEMENT");
        body.add("id", "");
        body.add("productAlias", "HP_POS3D");
        body.add("fileName", fileName);
        body.add("sliceSize", 1000000);
        body.add("checksum", DigestUtils.md5Hex(fileBinary));
        body.add("uploadPart", new ByteArrayResource(exportReportToPdf) {
            @Override
            public String getFilename() {
                return fileName;
            }
        });

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        String userUri = "https://upload..../upload"; //https://upload.dev.velacorp.vn/api/v1/upload
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(userUri);
        try {
            ResponseEntity<UploadResultDto> response = restTemplate.exchange(uriBuilder.toUriString(),
                    HttpMethod.POST, requestEntity, UploadResultDto.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Get store failed! Response: {}", response);
                return response.getBody();
            } else throw BIZException.buildBIZException(ErrType.CONSTRAINT, "", "");
        } catch (HttpServerErrorException ex) {
            logger.error("Error when validate: {}, Cause = {}", ex.getMessage(),
                    Arrays.toString(ex.getStackTrace()));
            throw ex;
        }
    }
}
