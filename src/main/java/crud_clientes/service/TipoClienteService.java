package crud_clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crud_clientes.dto.TipoClienteDto;
import crud_clientes.entity.TipoCliente;
import crud_clientes.repository.ITipoClienteRepository;

@Service
public class TipoClienteService {
	
	@Autowired
	private ITipoClienteRepository tipoClienteRepository;
	
	//Consulta todos los tipos clientes
	@Transactional(readOnly = true)
	public List<TipoCliente> consultarTiposCliente(){
		return (List<TipoCliente>)tipoClienteRepository.findAll();
	}
	
	//Consulta tipo cliente por id
	@Transactional(readOnly = true)
	public TipoCliente consultarTipoCliente(Long id) {
		return (TipoCliente) tipoClienteRepository.findById(id).orElse(null);
	}
	
	//Crear tipo cliente
	@Transactional
	public TipoCliente crearCliente(TipoClienteDto tipoClienteDto) {
		TipoCliente nuevoTipoCliente = new TipoCliente();
		
		nuevoTipoCliente.setTipoCliente(tipoClienteDto.getTipoCliente());
		
		return tipoClienteRepository.save(nuevoTipoCliente);
	}
	
	
	//Eliminar tipo cliente
	public void elimiarTipoCliente(Long id) {
		tipoClienteRepository.deleteById(id);
	}
	
	//Actualizar tipo cliente
	public TipoCliente actualizarTipoCliente(TipoClienteDto tipoClienteDto, Long id) {
		TipoCliente tipoClienteAActualizar = new TipoCliente();
		tipoClienteAActualizar.setId(id);
		tipoClienteAActualizar.setTipoCliente(tipoClienteDto.getTipoCliente());
		return tipoClienteRepository.save(tipoClienteAActualizar);
	}

}
