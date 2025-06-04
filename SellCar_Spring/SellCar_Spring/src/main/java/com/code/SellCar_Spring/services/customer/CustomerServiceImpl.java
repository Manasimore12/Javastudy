package com.code.SellCar_Spring.services.customer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.code.SellCar_Spring.dto.AnalyticsDTO;
import com.code.SellCar_Spring.dto.BidDTO;
import com.code.SellCar_Spring.dto.CarDTO;
import com.code.SellCar_Spring.dto.SearchCarDTO;
import com.code.SellCar_Spring.entities.Bid;
import com.code.SellCar_Spring.entities.Car;
import com.code.SellCar_Spring.entities.User;
import com.code.SellCar_Spring.enums.BidStatus;
import com.code.SellCar_Spring.repositories.BidRepository;
import com.code.SellCar_Spring.repositories.CarRepository;
import com.code.SellCar_Spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BidRepository bidRepository;

    @Override
    public boolean createCar(CarDTO carDTO) throws IOException {
        // Get the currently authenticated principal
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String email;
        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("User not authenticated properly");
        }
        
        Optional<User> optionalUser = userRepository.findFirstByEmail(email);
        if (optionalUser.isEmpty()) {
            System.out.println("User not found in database: " + email);
            return false; // Or throw a custom exception if preferred
        }

        // Fetch the User entity by email
        User user = userRepository.findFirstByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (carDTO.getImg() == null || carDTO.getImg().isEmpty()) {
            throw new RuntimeException("Image file is required");
        }

        // Create and set Car entity fields
        Car car = new Car();
        car.setName(carDTO.getName());
        car.setBrand(carDTO.getBrand());
        car.setPrice(carDTO.getPrice());
        car.setDescription(carDTO.getDescription());
        car.setType(carDTO.getType());
        car.setColor(carDTO.getColor());
        car.setUser(user); 
        car.setTransmission(carDTO.getTransmission());
        car.setSold(false);
        car.setYear(carDTO.getYear());
        car.setImg(carDTO.getImg().getBytes());
        carRepository.save(car);

        return true;
    }

	@Override
	public List<CarDTO> getAllCars() {
		
		return carRepository.findAll().stream().map(Car::getCarDTO).collect(Collectors.toList());
	}

	@Override
	public CarDTO getCarById(Long id) {
		Optional<Car> optionalCar = carRepository.findById(id);
		return optionalCar.map(Car::getCarDTO).orElse(null);
	}

	@Override
	public void deleteCar(Long id) {
		carRepository.deleteById(id);
	}

	@Override
	public boolean updateCar(Long id, CarDTO carDTO) throws IOException {
		Optional<Car> optionalCar=  carRepository.findById(id);
		if((optionalCar.isPresent())) {
			Car car = optionalCar.get();
			car.setName(carDTO.getName());
	        car.setBrand(carDTO.getBrand());
	        car.setPrice(carDTO.getPrice());
	        car.setDescription(carDTO.getDescription());
	        car.setColor(carDTO.getColor());
	        car.setType(carDTO.getType());
	        car.setTransmission(carDTO.getTransmission());
	        car.setYear(carDTO.getYear());
	        if (carDTO.getImg() != null)
	        	car.setImg(carDTO.getImg().getBytes());
	        carRepository.save(car);
	        return true;
		}
		return false;
	}

	@Override
	public List<CarDTO> searchCar(SearchCarDTO searchCarDTO) {
		Car car = new Car();
		car.setBrand(searchCarDTO.getBrand());
		car.setType(searchCarDTO.getType());
		car.setColor(searchCarDTO.getColor());
		car.setTransmission(searchCarDTO.getTransmission());
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll()
				.withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		Example<Car> carExample = Example.of(car,exampleMatcher);
		List<Car> cars =carRepository.findAll(carExample);
		
		return cars.stream().map(Car::getCarDTO).collect(Collectors.toList());
	}

	@Override
	public List<CarDTO> getMyCars(Long userId) {
		
		return carRepository.findAllByUserId(userId).stream().map(Car::getCarDTO).toList();
	}

	@Override
	public boolean bidACar(BidDTO bidDTO) throws IOException {
		Optional<Car> optionalCar = carRepository.findById(bidDTO.getCarId());
		Optional<User> optionalUser = userRepository.findById(bidDTO.getUserId());
		if(optionalCar.isPresent() && optionalUser.isPresent()) {
			Bid bid = new Bid();
			bid.setUser(optionalUser.get());
			bid.setCar(optionalCar.get());
			bid.setPrice(bidDTO.getPrice());
			bid.setBidStatus(BidStatus.PENDING);
			bidRepository.save(bid);
			return true;
		}
		return false;
	}

	@Override
	public List<BidDTO> getBidsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return  bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
	}

	@Override
	public List<BidDTO> getBidsByCarId(Long carId) {
		// TODO Auto-generated method stub
		return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDTO).collect(Collectors.toList());
	}

	@Override
	public boolean changeBidStatus(Long bidId, String status) {
		Optional<Bid> optionalBid = bidRepository.findById(bidId);
		if(optionalBid.isPresent()) {
			Bid existingBid = optionalBid.get();
			if(existingBid.getCar().getSold()) {
				return false;
			}
			if(Objects.equals(status, "Approve"))
				existingBid.setBidStatus(BidStatus.APPROVED);
			else 
				existingBid.setBidStatus(BidStatus.REJECTED);
			bidRepository.save(existingBid);
			return true;
		}
		return false;
	}

	@Override
	public AnalyticsDTO getAnalytics(Long userId) {
		AnalyticsDTO analyticsDTO = new AnalyticsDTO();
		analyticsDTO.setTotalCars(carRepository.countByUserId(userId));
		analyticsDTO.setSoldCars(carRepository.countByUserIdAndSoldTrue(userId));
		return analyticsDTO;
	}
}  