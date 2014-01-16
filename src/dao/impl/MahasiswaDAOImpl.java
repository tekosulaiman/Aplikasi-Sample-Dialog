package dao.impl;

import java.util.List;
import java.util.Map;

import model.Mahasiswa;

import org.springframework.stereotype.Repository;

import tools.GenenericDAO;
import dao.MahasiswaDAO;

/**
 * @author tombisnis@yahoo.com
 */
@Repository
public class MahasiswaDAOImpl extends GenenericDAO<Mahasiswa> implements MahasiswaDAO{
	
	@SuppressWarnings("unchecked")
	public List<Mahasiswa> getAllMahasiswas() {
		return getSessionFactory().getCurrentSession().createQuery("from Mahasiswa m").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Mahasiswa> getAllMahasiswasByRequest(Map<String, String> requestMap, boolean useLikeKeyword) {
		
		List<Mahasiswa> result = null; // init
    	
		final String hqlFrom = " FROM Mahasiswa ";
		String hqlWhere = " WHERE ";

    	Object params[] = new Object[requestMap.size()];
    	int a = 0;

    	for (Map.Entry<String, String> entry : requestMap.entrySet()) {
    			
    		String q = entry.getKey();
    		String v = entry.getValue();
        
    		if(useLikeKeyword == true){
		       
    			hqlWhere = hqlWhere + " " + q + " like ? AND ";
		        params[a] = "%" + v +"%";
			}else{
        		hqlWhere = hqlWhere + " " + q + " = ? AND ";
		        params[a] = v;            
        	}
    			
            a++;
    	}
    	
    	hqlWhere = hqlWhere + " 1=1 ";

    	final String hqlQuery = hqlFrom + hqlWhere;
    	result = getHibernateTemplate().find(hqlQuery, params);
    
    	return result;
	}
}