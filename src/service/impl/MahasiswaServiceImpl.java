package service.impl;

import java.util.List;
import java.util.Map;

import model.Mahasiswa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import service.MahasiswaService;
import dao.MahasiswaDAO;

/**
 * @author tombisnis@yahoo.com
 */
@Service
public class MahasiswaServiceImpl implements MahasiswaService{
	
	@Autowired
    MahasiswaDAO mahasiswaDAO;

	public MahasiswaDAO getMahasiswaDAO() {
		return mahasiswaDAO;
	}

	public void setMahasiswaDAO(MahasiswaDAO mahasiswaDAO) {
		this.mahasiswaDAO = mahasiswaDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void save(Mahasiswa mahasiswa) {
		mahasiswaDAO.save(mahasiswa);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void update(Mahasiswa mahasiswa) {
		mahasiswaDAO.update(mahasiswa);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public void delete(Mahasiswa mahasiswa) {
		mahasiswaDAO.delete(mahasiswa);
	}

	@Override
	public List<Mahasiswa> getAllMahasiswas() {
		return mahasiswaDAO.getAllMahasiswas();
	}

	@Override
	public List<Mahasiswa> getAllMahasiswasByRequest(Map<String, String> requestMap, boolean useLikeKeyword) {
		return mahasiswaDAO.getAllMahasiswasByRequest(requestMap, useLikeKeyword);
	}
}