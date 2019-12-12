- notification using FCM
- dang nhap fb hoac google
- su dung dubbo
- export excel xu dung template

- https://www.google.com/settings/security/lesssecureapps : link cai dat email nhan nhung ung dung kem an toan

```java
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAllByRolesIn(List<Role> roles);            //1 

    @Query("SELECT p FROM User p LEFT JOIN FETCH p.roles")  
    List<User> findWithoutNPlusOne();                       //2

    @EntityGraph(attributePaths = {"roles"})                      
    List<User> findAll();                                   //3
}
// 1 - N+1 queries are issued at database level.
// 2 - using left join fetch, we resolve the N+1 problem
// 3 - using attributePaths, Spring Data JPA avoids N+1 problem
```