package service;

import java.util.List;
import java.util.Map;

import model.Mahasiswa;

/**
 * @author tombisnis@yahoo.com
 */
public interface MahasiswaService {
	public void save(Mahasiswa mahasiswa);
    public void update(Mahasiswa mahasiswa);
    public void delete(Mahasiswa mahasiswa);
    public List<Mahasiswa> getAllMahasiswas();
    public List<Mahasiswa> getAllMahasiswasByRequest(Map<String, String> requestMap, boolean useLikeKeyword);
}