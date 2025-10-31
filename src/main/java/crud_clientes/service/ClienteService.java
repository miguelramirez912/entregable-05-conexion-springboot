package crud_clientes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import crud_clientes.dto.ClienteDto;
import crud_clientes.entity.Cliente;
import crud_clientes.repository.IClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	//Consulta de todos los clientes
	@Transactional(readOnly = true)
	public List<Cliente> findAll(){
		return (List<Cliente>)clienteRepository.findAll();
	}
	
	//Consulta por Id
	@Transactional(readOnly = true)
	public Cliente findById(Long id) {
		return (Cliente) clienteRepository.findById(id).orElse(null);
	}
	
	//CrearCliente
	@Transactional
	public Cliente createCliente(ClienteDto clienteDto) {
		Cliente clienteEntity = new Cliente();
		clienteEntity.setNombre(clienteDto.getNombre());
		clienteEntity.setApellido(clienteDto.getApellido());
		clienteEntity.setEmail(clienteDto.getEmail());
		
		return clienteRepository.save(clienteEntity);
	}
	
	//Eliminar Cliente
	public void deleteCliente(Long id) {
		clienteRepository.deleteById(id);
	}
}
