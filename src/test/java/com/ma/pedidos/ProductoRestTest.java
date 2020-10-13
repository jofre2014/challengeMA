package com.ma.pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ma.pedidos.dto.DetalleDto;
import com.ma.pedidos.dto.ErrorHandler;
import com.ma.pedidos.dto.ErrorMessage;
import com.ma.pedidos.dto.PedidoDto;
import com.ma.pedidos.models.dao.IPedidoCabeceraDao;
import com.ma.pedidos.models.dao.IPedidoDetalleDao;
import com.ma.pedidos.models.dao.IProductoDao;
import com.ma.pedidos.models.entity.Producto;

@SpringBootTest(classes = { MainApp.class},
        webEnvironment = RANDOM_PORT)
public class ProductoRestTest {
	
	private final String producto1 = "89efb206-2aa6-4e21-8a23-5765e3de1f31";
	private final String prod1Nombre = "Jamón y morrones";
	private final String prod1DescLarga = "Mozzarella, jamón, morrones y aceitunas verdes";
	private final String prod1DescCorta = "Pizza de jamón y morrones";
	private final Double prod1PrecioUni = 550.00;
	

	
	private final String producto2 = "e29ebd0c-39d2-4054-b0f4-ed2d0ea089a1";
	private final String prod2Nombre = "Palmitos";
	private final String prod2DescLarga = "Mozzarella, jamón, palmitos";
	private final String prod2DescCorta = "Pizza Palmitos";
	private final Double prod2PrecioUni = 600.00;
	
	
	private final String direccion = "Dorton Road 80";
	private final String email = "tsayb@opera.com";
	private final String telefono = "(0351) 48158101";
	private final String horario = "21:00";
	
	private final String direccion2 = "Artisan Hill 47";
	private final String email2 = "ghathawayg@home.pl";
	private final String telefono2 = "(0358) 48997013";
	private final String horario2 = "22:30";	

	
	private final String urlProductos = "/productos";
	private final String urlPedidos = "/pedidos";
	
	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IPedidoDetalleDao pedidoDetalleDao;
	

	@Autowired
	private IPedidoCabeceraDao pedidoCabeceraDao;
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	
	
	
	
	@BeforeEach
	public void setup() {
		System.out.println("Setup");
		
		pedidoCabeceraDao.deleteAll();
		pedidoDetalleDao.deleteAll();
		productoDao.deleteAll();
		
		
		
		Producto producto = new Producto();
		producto.setProductoId(producto1);
		producto.setNombre(prod1Nombre);
		producto.setDescripcionCorta(prod1DescCorta);
		producto.setDescripcionLarga(prod1DescLarga);
		producto.setPrecioUnitario(prod1PrecioUni);
		
		productoDao.save(producto);
		
		Producto producto_2 = new Producto();
		producto_2.setProductoId(producto2);
		producto_2.setNombre(prod2Nombre);
		producto_2.setDescripcionCorta(prod2DescCorta);
		producto_2.setDescripcionLarga(prod2DescLarga);
		producto_2.setPrecioUnitario(prod2PrecioUni);
		
		productoDao.save(producto_2);
		
		
		
	}
	
	
	@Test
	public void productoAlta() {
	
		Producto producto = new Producto();
		producto.setProductoId(producto2);
		producto.setNombre(prod2Nombre);
		producto.setDescripcionCorta(prod2DescCorta);
		producto.setDescripcionLarga(prod2DescLarga);
		producto.setPrecioUnitario(prod2PrecioUni);
		
		
		ResponseEntity<Producto> response =	this.restTemplate.exchange(
                urlProductos,
                HttpMethod.POST,
                new HttpEntity<Producto>(producto),
                Producto.class);
				
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		
	}
	
	@Test
	public void productoModificar() {
		
		Producto producto = new Producto();
		producto.setNombre(prod1Nombre);
		producto.setDescripcionCorta(prod1DescCorta);
		producto.setDescripcionLarga(prod1DescLarga);
		producto.setPrecioUnitario(1200.00);
		
		String url = urlProductos+"/"+producto1;

	        final ResponseEntity<Void> response = this.restTemplate
	                .exchange(url, HttpMethod.PUT, new HttpEntity<Producto>(producto), Void.class);
	        
	        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
		
	@Test
	public void productoConsulta() {
		
		String url = urlProductos+"/"+producto1;
		
		ResponseEntity<Producto> response =	this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Producto.class);
		
		Producto productoResponse = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		assertEquals(producto1, productoResponse.getProductoId());
		assertEquals(prod1Nombre, productoResponse.getNombre());
		assertEquals(prod1DescCorta, productoResponse.getDescripcionCorta());
		assertEquals(prod1DescLarga, productoResponse.getDescripcionLarga());
		assertEquals(prod1PrecioUni, productoResponse.getPrecioUnitario());
		
	}
	
	@Test
	public void productoNoExiste() {
		
		String url = urlProductos+"/89efb206-2aa6-4e21-8a23-5765e3de1f30";
		
		ResponseEntity<ErrorMessage> response =	this.restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ErrorMessage.class);
		
		ErrorMessage productoResponse = response.getBody();
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals("Producto no encontrado", productoResponse.getError());
		
	}
	
	@Test
	public void productoBorrar() {	
		
		
		Producto producto = new Producto();
		producto.setProductoId("89efb206-2aa6-4e21-8a23-5765e3de1te4");
		producto.setNombre("Simple");
		producto.setDescripcionCorta("Pizza Simple");
		producto.setDescripcionLarga("Mozzarela y aceitunas verdes");
		producto.setPrecioUnitario(300.00);
		
		productoDao.save(producto);
		
		String url = urlProductos+"/89efb206-2aa6-4e21-8a23-5765e3de1te4";
		
		ResponseEntity<Void> response =	this.restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                Void.class);
		
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());		
		
	}
	
	
	@Test
	public void crearPedido() {
		
		List<DetalleDto> detalleList = new ArrayList<>();
		
		DetalleDto detalleDto = new DetalleDto();
		detalleDto.setProducto(producto1);
		detalleDto.setCantidad(1);
					
		detalleList.add(detalleDto);
		
		DetalleDto detalleDto2 = new DetalleDto();
		detalleDto2.setProducto(producto2);
		detalleDto2.setCantidad(1);
		
		detalleList.add(detalleDto2);
		
		
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setDireccion(direccion);
		pedidoDto.setEmail(email);
		pedidoDto.setTelefono(telefono);
		pedidoDto.setHorario(LocalTime.parse(horario));
		pedidoDto.setDetalle(detalleList);
		
				
		ResponseEntity<PedidoDto> response =	this.restTemplate.exchange(
                urlPedidos,
                HttpMethod.POST,
                new HttpEntity<PedidoDto>(pedidoDto),
                PedidoDto.class);
		
		PedidoDto pedidoResponse = response.getBody();
		
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(direccion, pedidoResponse.getDireccion());
		assertEquals(email, pedidoResponse.getEmail());
		assertEquals(detalleList.size(), pedidoResponse.getDetalle().size());
		assertEquals(1150.00, pedidoResponse.getTotal());
		assertEquals(Boolean.FALSE, pedidoResponse.getDescuento());
		assertEquals("PENDIENTE", pedidoResponse.getEstado());
		assertEquals(1, pedidoResponse.getDetalle().stream().filter(detalle -> detalle.getProducto().equalsIgnoreCase(producto1) && 
				detalle.getNombre().equals(prod1DescCorta) && detalle.getCantidad() ==1 &&  detalle.getImporte().compareTo(prod1PrecioUni)  == 0).count());
		assertEquals(1, pedidoResponse.getDetalle().stream().filter(detalle -> detalle.getProducto().equalsIgnoreCase(producto2) && 
				detalle.getNombre().equals(prod2DescCorta) && detalle.getCantidad() ==1 &&  detalle.getImporte().compareTo(prod2PrecioUni)  == 0).count());
		
		
	}
	
	@Test
	public void crearPedidoConDescuento() {
		
		List<DetalleDto> detalleList = new ArrayList<>();
		
		DetalleDto detalleDto = new DetalleDto();
		detalleDto.setProducto(producto1);
		detalleDto.setCantidad(2);
					
		detalleList.add(detalleDto);
		
		DetalleDto detalleDto2 = new DetalleDto();
		detalleDto2.setProducto(producto2);
		detalleDto2.setCantidad(2);
		
		detalleList.add(detalleDto2);
		
		
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setDireccion(direccion);
		pedidoDto.setEmail(email);
		pedidoDto.setTelefono(telefono);
		pedidoDto.setHorario(LocalTime.parse(horario));
		pedidoDto.setDetalle(detalleList);
		
				
		ResponseEntity<PedidoDto> response =	this.restTemplate.exchange(
                urlPedidos,
                HttpMethod.POST,
                new HttpEntity<PedidoDto>(pedidoDto),
                PedidoDto.class);
		
		PedidoDto pedidoResponse = response.getBody();
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(direccion, pedidoResponse.getDireccion());
		assertEquals(email, pedidoResponse.getEmail());
		assertEquals(detalleList.size(), pedidoResponse.getDetalle().size());
		assertEquals(1610.00, pedidoResponse.getTotal());
		assertEquals(Boolean.TRUE, pedidoResponse.getDescuento());
		assertEquals("PENDIENTE", pedidoResponse.getEstado());
		assertEquals(1, pedidoResponse.getDetalle().stream().filter(detalle -> detalle.getProducto().equalsIgnoreCase(producto1) && 
				detalle.getNombre().equals(prod1DescCorta) && detalle.getCantidad() ==2 &&  detalle.getImporte().compareTo(prod1PrecioUni*2)  == 0).count());
		assertEquals(1, pedidoResponse.getDetalle().stream().filter(detalle -> detalle.getProducto().equalsIgnoreCase(producto2) && 
				detalle.getNombre().equals(prod2DescCorta) && detalle.getCantidad() ==2 &&  detalle.getImporte().compareTo(prod2PrecioUni*2)  == 0).count());
		
		
	}
	
	@Test
	public void crearPedidoConErrores() {
		List<DetalleDto> detalleList = new ArrayList<>();
		
		DetalleDto detalleDto = new DetalleDto();
		detalleDto.setProducto(producto1);
		
					
		detalleList.add(detalleDto);
		
		DetalleDto detalleDto2 = new DetalleDto();
		detalleDto2.setProducto(producto2);
		detalleDto2.setCantidad(2);
		
		detalleList.add(detalleDto2);
		
		
		PedidoDto pedidoDto = new PedidoDto();
		
		pedidoDto.setEmail(email);
		pedidoDto.setTelefono(telefono);
		pedidoDto.setHorario(LocalTime.parse(horario));
		pedidoDto.setDetalle(detalleList);
		
				
		ResponseEntity<ErrorHandler> response =	this.restTemplate.exchange(
                urlPedidos,
                HttpMethod.POST,
                new HttpEntity<PedidoDto>(pedidoDto),
                ErrorHandler.class);
		
		ErrorHandler pedidoResponse = response.getBody();
		
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(1, pedidoResponse.getErrores().stream().filter(error -> error.getError().equals("la direccion no puede estar nula")).count());
		assertEquals(1, pedidoResponse.getErrores().stream().filter(error -> error.getError().equals("falta ingresar cantidad")).count());
		
		
		
	}
	
	@Test
	public void ListarPedidosPorFecha() {
	
		
		crearPedidos();
		
		ParameterizedTypeReference<List<PedidoDto>> typeRef = new ParameterizedTypeReference<List<PedidoDto>>() {};
		
		String url = urlPedidos+"?fecha="+LocalDate.now().toString();
		
		ResponseEntity<List<PedidoDto>> response = 
				restTemplate.exchange(url, HttpMethod.GET, null, typeRef);
			
		List<PedidoDto> pedidoResponse = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(LocalTime.parse(horario), pedidoResponse.stream().findFirst().get().getHorario());
		assertEquals(1, pedidoResponse.stream().filter(pedido -> pedido.getFecha().equals(LocalDate.now()) && pedido.getDireccion().equals(direccion)
				&& pedido.getEmail().equals(email) && pedido.getTelefono().equals(telefono) && pedido.getHorario().equals(LocalTime.parse(horario))
				&& pedido.getDescuento().equals(Boolean.TRUE) && pedido.getTotal().compareTo(1610.0) == 0).count());
		assertEquals(1, pedidoResponse.stream().filter(pedido -> pedido.getFecha().equals(LocalDate.now()) && pedido.getDireccion().equals(direccion2)
				&& pedido.getEmail().equals(email2) && pedido.getTelefono().equals(telefono2) && pedido.getHorario().equals(LocalTime.parse(horario2))
				&& pedido.getDescuento().equals(Boolean.FALSE) && pedido.getTotal().compareTo(550.0) == 0).count());
		
		
		
		
	}
	
	private void crearPedidos() {
		
		List<DetalleDto> detalleList = new ArrayList<>();
		
		DetalleDto detalleDto = new DetalleDto();
		detalleDto.setProducto(producto1);
		detalleDto.setCantidad(2);
					
		detalleList.add(detalleDto);
		
		DetalleDto detalleDto2 = new DetalleDto();
		detalleDto2.setProducto(producto2);
		detalleDto2.setCantidad(2);
		
		detalleList.add(detalleDto2);
		
		
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setDireccion(direccion);
		pedidoDto.setEmail(email);
		pedidoDto.setTelefono(telefono);
		pedidoDto.setHorario(LocalTime.parse(horario));
		pedidoDto.setDetalle(detalleList);
		
				
		this.restTemplate.exchange(
                urlPedidos,
                HttpMethod.POST,
                new HttpEntity<PedidoDto>(pedidoDto),
                PedidoDto.class);
		
		
		List<DetalleDto> detalleList2 = new ArrayList<>();
		
		DetalleDto detalleDto3 = new DetalleDto();
		detalleDto3.setProducto(producto1);
		detalleDto3.setCantidad(1);
					
		detalleList2.add(detalleDto3);
				
		
		PedidoDto pedidoDto2 = new PedidoDto();
		pedidoDto2.setDireccion(direccion2);
		pedidoDto2.setEmail(email2);
		pedidoDto2.setTelefono(telefono2);
		pedidoDto2.setHorario(LocalTime.parse(horario2));
		pedidoDto2.setDetalle(detalleList2);
		
				
		this.restTemplate.exchange(
                urlPedidos,
                HttpMethod.POST,
                new HttpEntity<PedidoDto>(pedidoDto2),
                PedidoDto.class);
		
	}
	
	

}
