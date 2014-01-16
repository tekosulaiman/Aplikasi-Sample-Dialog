package vm;

import java.util.HashMap;
import java.util.List;

import model.Mahasiswa;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import service.MahasiswaService;

/**
 * @author tombisnis@yahoo.com
 */
public class MahasiswaVM {
	
	@Wire("#listboxMahasiswa")
	private Listbox listboxMahasiswa;
	
	@Wire("#textboxNpm")
	private Textbox textboxNpm;
	
	@Wire("#textboxNama")
	private Textbox textboxNama;
	
	private Mahasiswa mahasiswa;
	private List<Mahasiswa> mahasiswas;
	@WireVariable
	private MahasiswaService mahasiswaService = (MahasiswaService) SpringUtil.getBean("mahasiswaService");

	public Mahasiswa getMahasiswa() {
		return mahasiswa;
	}

	public void setMahasiswa(Mahasiswa mahasiswa) {
		this.mahasiswa = mahasiswa;
	}

	public List<Mahasiswa> getMahasiswas() {
		return mahasiswas;
	}

	public void setMahasiswas(List<Mahasiswa> mahasiswas) {
		this.mahasiswas = mahasiswas;
	}

	public MahasiswaService getMahasiswaService() {
		return mahasiswaService;
	}

	public void setMahasiswaService(MahasiswaService mahasiswaService) {
		this.mahasiswaService = mahasiswaService;
	}

	@AfterCompose
	public void initComponents(@ContextParam(ContextType.VIEW) Component component,
		@ExecutionArgParam("object") Object object,
		@ExecutionArgParam("mahasiswa") Mahasiswa mahasiswa) {

		Selectors.wireComponents(component, this, false);
		
		String windowID = (String) object;

		if (windowID == "windowDialog") {
			
			if(mahasiswa == null){
				
				this.mahasiswa = new Mahasiswa();
			}else {
				
				this.mahasiswa = mahasiswa;
				
				textboxNpm.setDisabled(true);
			}
		}else {
			
			setMahasiswas(getMahasiswaService().getAllMahasiswas());
		}
	}
	

	@Command
	public void doView(){
		final HashMap<String, Object> mapInit = new HashMap<String, Object>();
		
		mapInit.put("object", "windowDialog");
		mapInit.put("mahasiswa", mahasiswa);

		Executions.createComponents("/WEB-INF/pages/mahasiswaDialog.zul", null, mapInit);
	}
	
	@Command
	public void doNew(){
		final HashMap<String, Object> mapInit = new HashMap<String, Object>();
		
		mapInit.put("object", "windowDialog");

		Executions.createComponents("/WEB-INF/pages/mahasiswaDialog.zul", null, mapInit);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	public void doDelete(){
		Messagebox.show("Do you really want to remove item?", "Confirm", Messagebox.OK | Messagebox.CANCEL, Messagebox.EXCLAMATION, new EventListener() {
		 	@Override
		    public void onEvent(Event event) throws Exception {    	
		 		if (((Integer) event.getData()).intValue() == Messagebox.OK) {
		 			for(Mahasiswa mahasiswa : mahasiswas){
		 				mahasiswaService.delete(mahasiswa);
		 			}
		 			
		 			BindUtils.postGlobalCommand(null, null, "refreshAfterSave", null);
		 		}		
		 	}
	     });
	}
	
	@Command
	public void doSaveDialog(){
		if(mahasiswa.getNpm() == null){
			System.out.println("Save");
			
			mahasiswa.setNpm(textboxNpm.getValue());
			mahasiswa.setNama(textboxNama.getValue());
			
			mahasiswaService.save(mahasiswa);
			
			BindUtils.postGlobalCommand(null, null, "refreshAfterSave", null);
		}else{
			System.out.println("Update");
			
			mahasiswa.setNama(textboxNama.getValue());
			
			mahasiswaService.update(mahasiswa);
			
			BindUtils.postGlobalCommand(null, null, "refreshAfterSave", null);
		}
	}
	
	@GlobalCommand
	@NotifyChange("mahasiswas")
	public void refreshAfterSave(){
		mahasiswas = mahasiswaService.getAllMahasiswas();
	}
}