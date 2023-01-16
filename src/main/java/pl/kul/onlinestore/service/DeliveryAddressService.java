package pl.kul.onlinestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.entity.order.DeliveryAddress;
import pl.kul.onlinestore.exception.DeliveryAdressNotFoundException;
import pl.kul.onlinestore.repository.DeliveryAddressRepository;

@Service
public class DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    public DeliveryAddressService(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    public DeliveryAddress fetchDeliveryAddressById(Long id){
        return deliveryAddressRepository.findById(id).orElseThrow(
                () -> new DeliveryAdressNotFoundException("Adres nie byl znaleziony")
        );
    }

    public void createDeliveryAddress(DeliveryAddress deliveryAddress){
        deliveryAddressRepository.save(deliveryAddress);
    }

}
