package com.liu.person.user;

import org.springframework.data.envers.repository.support.EnversRevisionRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends EnversRevisionRepository<User,String,Integer>, PagingAndSortingRepository<User, String>{

	//http://localhost:8088/api/users/search/findById?id=0011dd;id不对，会报404
	User findById(@Param("id") String id);
	
	User findByNameLike(@Param("name") String name);
	
	User findByNameContaining(@Param("name") String name);
	
}
