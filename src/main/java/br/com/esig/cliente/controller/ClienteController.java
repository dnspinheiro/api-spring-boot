package br.com.esig.cliente.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.esig.cliente.entity.Cliente;
import br.com.esig.cliente.repository.ClienteRepository;

@RestController
public class ClienteController {
    @Autowired
    private ClienteRepository _clienteRepository;

    // @RequestMapping(value = "/", method = RequestMethod.GET)
    // public String Get() {
    // return "olaaa";
    // }

    // @RequestMapping(value = "/ola", method = RequestMethod.GET)
    // public String Get() {
    // return "olaaa";
    // }

    @CrossOrigin
    @RequestMapping(value = "/cliente", method = RequestMethod.GET)
    public List<Cliente> Get() {
        return _clienteRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> GetById(@PathVariable(value = "id") long id) {
        Optional<Cliente> cliente = _clienteRepository.findById(id);
        if (cliente.isPresent())
            return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/cliente", method = RequestMethod.POST)
    public Cliente Post(@Valid @RequestBody Cliente cliente) {
        return _clienteRepository.save(cliente);
    }

    @CrossOrigin
    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> Put(@PathVariable(value = "id") long id, @Valid @RequestBody Cliente newCliente) {
        Optional<Cliente> oldCliente = _clienteRepository.findById(id);
        if (oldCliente.isPresent()) {
            Cliente cliente = oldCliente.get();
            cliente.setNome(newCliente.getNome());
            cliente.setEmail(newCliente.getEmail());
            cliente.setTelefone(newCliente.getTelefone());
            cliente.setCpf(newCliente.getCpf());
            cliente.setData(newCliente.getData());
            _clienteRepository.save(cliente);
            return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @CrossOrigin
    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") long id) {
        Optional<Cliente> cliente = _clienteRepository.findById(id);
        if (cliente.isPresent()) {
            _clienteRepository.delete(cliente.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}