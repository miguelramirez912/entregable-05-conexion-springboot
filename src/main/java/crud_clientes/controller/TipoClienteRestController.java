package crud_clientes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import crud_clientes.dto.TipoClienteDto;
import crud_clientes.entity.TipoCliente;
import crud_clientes.service.TipoClienteService;

@RestController
@RequestMapping("/api")
public class TipoClienteRestController {
	
	@Autowired
	TipoClienteService tipoClienteService;
	
	//Consultar todos los tipos de clientes
	@GetMapping("/tipo-cliente")
	@ResponseStatus(HttpStatus.OK)
	public List<TipoCliente> consultar(){
		return tipoClienteService.consultarTiposCliente();
	}
	
	//Consultar tipo de cliente por id
	@GetMapping("/tipo-cliente/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> consultarTipoClientePorId(@PathVariable Long id) {
		TipoCliente tipoClienteEncontrado = null;
		String response = "";
		
		try {
			
			tipoClienteEncontrado = tipoClienteService.consultarTipoCliente(id);
			
		}catch(DataAccessException e) {
			response = "Error al realizar la consulta";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(tipoClienteEncontrado == null) {
			response = "El tipo de cliente con el ID".concat(id.toString()).concat(" no existe en la base de datos");
			return new ResponseEntity<String>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipoCliente>(tipoClienteEncontrado, HttpStatus.OK);
	}
	
	//Borrar tipo de cliente por id
	@DeleteMapping("/tipo-cliente/{id}")
	public ResponseEntity<?> borrarTipoClientePorId(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try {
			TipoCliente tipoClienteABorrar = this.tipoClienteService.consultarTipoCliente(id);
			
			if(tipoClienteABorrar == null) {
				response.put("mensaje", "Error al eliminar, el tipo de cliente no existe en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			tipoClienteService.elimiarTipoCliente(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar en base de datos");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "Tipo de Cliente eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	//Crear tipo de cliente
	@PostMapping("/tipo-cliente")
	public ResponseEntity<?> crearCliente(@RequestBody TipoClienteDto tipoClienteDto) {
		
		TipoCliente nuevoTipoCliente = null;
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			nuevoTipoCliente = this.tipoClienteService.crearCliente(tipoClienteDto);
			
		}catch(DataAccessException e){
			response.put("mensaje", "No se pudo crear el cliente");
			response.put("error", e.getMessage().concat(e.getMostSpecificCause().getLocalizedMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El Tipo de Cliente creado con exito, con el ID");
		response.put("tipoCliente", nuevoTipoCliente);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		
	}
	
	//Actualizar tipo de cliente
	@PostMapping("/tipo-cliente/{id}")
	public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody TipoClienteDto tipoClienteDto){
		
		TipoCliente tipoClienteAActualizar = null;
		TipoCliente tipoClienteActualizado = null;
		String response = "";
		
		try {
			//Encontrar cliente en BD
			tipoClienteAActualizar = this.tipoClienteService.consultarTipoCliente(id);
			
			//Guardarlo con save (metodo
			tipoClienteActualizado =  this.tipoClienteService.actualizarTipoCliente(tipoClienteDto, id);
			
		}catch(DataAccessException e){
			response = "Error al realizar la consulta";
			response = response.concat(e.getMessage().concat(e.getMostSpecificCause().toString()));
			return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);		
		}
		
		if(tipoClienteAActualizar == null) {
			response = "Error al realizar la actualización. El tipo de cliente no fue encontrado";
			return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<TipoCliente>(tipoClienteActualizado, HttpStatus.OK);
		
	}
}
