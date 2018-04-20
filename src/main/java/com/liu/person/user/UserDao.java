package com.liu.person.user;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDao {
	
	@PersistenceContext(unitName = "demo")
	private EntityManager entityManager;
	
	/**
	 * 模糊分页查询
	 * @param name
	 * @param pageable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> findPageUserByLike(String name,Pageable pageable){
		Map<String ,Object> map = new HashMap<String ,Object>();
		
		String sql = "select * from users t where name like '%"+name+"%' order by name desc";
		Query query = entityManager.createNativeQuery(sql,User.class);
		query.setFirstResult(pageable.getPageNumber()*pageable.getPageSize());
		query.setMaxResults(pageable.getPageSize());
		List<User> list = query.getResultList();
		
		sql = "select count(1) from users t where name like '%"+name+"%'";
		query = entityManager.createNativeQuery(sql);
		BigDecimal num = (BigDecimal) query.getSingleResult();
		
        map.put("num", num);
        map.put("content", list);
        return map;
	}
	
	

	@Transactional
	public void saveTestUser(User user){
		String sql = "update users t set birth = '"+user.getBirth()+"', email = 'yyyy@bestvike.com' where id = '"+user.getId()+"'";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}
	
	public User findOne(String id){
		String sql = "select * from  users t where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		List<User> list = query.getResultList();
		return list.get(0);
	}
}
