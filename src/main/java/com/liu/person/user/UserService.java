package com.liu.person.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.liu.commons.BusinessException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDao userDao;
	
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(String id){
		return userRepository.findOne(id);
	}
	
	//@Transactional(rollbackFor=Exception.class)//对检查异常进行回滚
	//@Transactional(noRollbackFor=RuntimeException.class)//对运行时异常不回滚
	@Transactional
	//@Transactional(propagation=Propagation.NOT_SUPPORTED)//不开启事务
	//@Transactional(propagation=Propagation.REQUIRED)//已经处在一个事务中，那么加入到这个事务，否则创建一个事务
	//@Transactional (propagation = Propagation.REQUIRED,timeout=3)//设置超时时间/单位s
	//@Transactional (propagation = Propagation.REQUIRED,readOnly=true)//只读，不能更新、删除
	//@Transactional (propagation = Propagation.REQUIRED,isolation=Isolation.DEFAULT)//用我们数据库的默认级别
//	        ① Serializable (串行化)：可避免脏读、不可重复读、幻读的发生。
//	　　② Repeatable read (可重复读)：可避免脏读、不可重复读的发生。
//	　　③ Read committed (读已提交)：可避免脏读的发生。(oracle默认级别)
//	　　④ Read uncommitted (读未提交)：最低级别，任何情况都无法保证。
	public void saveUser(User user){
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		//saveTestUser(user);//没事务不会提交
		userRepository.save(user);
//		throw new NullPointerException();
		//throw new RuntimeException();
	}
	
	public Page<User> querPageUser(Pageable pageable){
		return userRepository.findAll(pageable);
	}
	
	
	public Map<String,Object> findPageUserByLike(String name ,Pageable pageable){
		return userDao.findPageUserByLike(name,pageable);
	}
	
	//@Transactional
	public void saveTestUser(User user){
		//userRepository.save(user);//jpa
		userDao.saveTestUser(user);//原生
	}
	
	//@Transactional
	public void saveTestUser1(User user){
		//saveTestUser(user);
		userRepository.save(user);//jpa
		//throw new BusinessException("C0322","业务不符合要求");
		throw new NullPointerException();
//		userDao.saveTestUser(user);//原生
//		User user3 = userDao.findOne("0023");
//		User user2 = userRepository.findOne("0023");
	}
	
}
