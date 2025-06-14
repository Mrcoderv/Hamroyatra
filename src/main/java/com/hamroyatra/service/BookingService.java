package com.hamroyatra.service;

import com.hamroyatra.model.Booking;
import com.hamroyatra.model.TourPackage;
import com.hamroyatra.model.User;
import com.hamroyatra.repository.BookingRepository;
import com.hamroyatra.repository.TourPackageRepository;
import com.hamroyatra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class BookingService {
<<<<<<< HEAD

    private static final Logger logger = Logger.getLogger(BookingService.class.getName());

    private final BookingRepository bookingRepository;
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public BookingService(BookingRepository bookingRepository,
                          TourPackageRepository tourPackageRepository,
                          UserRepository userRepository) {
=======
    
    private static final Logger logger = Logger.getLogger(BookingService.class.getName());
    
    private final BookingRepository bookingRepository;
    private final TourPackageRepository tourPackageRepository;
    private final UserRepository userRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    public BookingService(BookingRepository bookingRepository, 
                         TourPackageRepository tourPackageRepository,
                         UserRepository userRepository) {
>>>>>>> 96ed910 (Initial commit or update project)
        this.bookingRepository = bookingRepository;
        this.tourPackageRepository = tourPackageRepository;
        this.userRepository = userRepository;
    }
<<<<<<< HEAD

=======
    
>>>>>>> 96ed910 (Initial commit or update project)
    @Transactional
    public void ensureSpecialRequirementsColumnExists() {
        try {
            // Check if the column exists
            entityManager.createNativeQuery(
<<<<<<< HEAD
                    "SELECT special_requirements FROM bookings LIMIT 1"
=======
                "SELECT special_requirements FROM bookings LIMIT 1"
>>>>>>> 96ed910 (Initial commit or update project)
            ).getResultList();
        } catch (Exception e) {
            // Column doesn't exist, create it
            logger.info("Adding special_requirements column to bookings table");
            entityManager.createNativeQuery(
<<<<<<< HEAD
                    "ALTER TABLE bookings ADD COLUMN IF NOT EXISTS special_requirements TEXT"
            ).executeUpdate();
        }
    }

=======
                "ALTER TABLE bookings ADD COLUMN IF NOT EXISTS special_requirements TEXT"
            ).executeUpdate();
        }
    }
    
>>>>>>> 96ed910 (Initial commit or update project)
    public List<Booking> getAllBookings() {
        try {
            ensureSpecialRequirementsColumnExists();
            return bookingRepository.findAll();
        } catch (DataAccessException e) {
            logger.warning("Error getting all bookings: " + e.getMessage());
            return Collections.emptyList();
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> 96ed910 (Initial commit or update project)
    public Optional<Booking> getBookingById(Long id) {
        try {
            ensureSpecialRequirementsColumnExists();
            return bookingRepository.findById(id);
        } catch (DataAccessException e) {
            logger.warning("Error getting booking by ID " + id + ": " + e.getMessage());
            return Optional.empty();
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> 96ed910 (Initial commit or update project)
    public List<Booking> getBookingsByCustomerName(String customerName) {
        try {
            ensureSpecialRequirementsColumnExists();
            return bookingRepository.findByCustomerNameContainingIgnoreCase(customerName);
        } catch (DataAccessException e) {
            logger.warning("Error getting bookings by customer name: " + e.getMessage());
            return Collections.emptyList();
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> 96ed910 (Initial commit or update project)
    public List<Booking> getBookingsByEmail(String email) {
        try {
            ensureSpecialRequirementsColumnExists();
            return bookingRepository.findByEmail(email);
        } catch (DataAccessException e) {
            logger.warning("Error getting bookings by email: " + e.getMessage());
            return Collections.emptyList();
        }
    }
<<<<<<< HEAD

    public Booking createBooking(Booking booking, Long tourPackageId) {
        Optional<TourPackage> tourPackageOpt = tourPackageRepository.findById(tourPackageId);

        if (tourPackageOpt.isPresent()) {
            TourPackage tourPackage = tourPackageOpt.get();
            booking.setTourPackage(tourPackage);

=======
    
    public Booking createBooking(Booking booking, Long tourPackageId) {
        Optional<TourPackage> tourPackageOpt = tourPackageRepository.findById(tourPackageId);
        
        if (tourPackageOpt.isPresent()) {
            TourPackage tourPackage = tourPackageOpt.get();
            booking.setTourPackage(tourPackage);
            
>>>>>>> 96ed910 (Initial commit or update project)
            // Calculate total amount
            BigDecimal packagePrice = tourPackage.getPrice();
            int numberOfPeople = booking.getNumberOfPeople();
            BigDecimal totalAmount = packagePrice.multiply(BigDecimal.valueOf(numberOfPeople));
<<<<<<< HEAD

            booking.setTotalAmount(totalAmount);
            booking.setBookingDate(LocalDate.now());
            booking.setStatus(Booking.BookingStatus.PENDING);

=======
            
            booking.setTotalAmount(totalAmount);
            booking.setBookingDate(LocalDate.now());
            booking.setStatus(Booking.BookingStatus.PENDING);
            
>>>>>>> 96ed910 (Initial commit or update project)
            // Associate with current user if authenticated
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                String username = auth.getName();
                Optional<User> userOpt = userRepository.findByUsername(username);
<<<<<<< HEAD

=======
                
>>>>>>> 96ed910 (Initial commit or update project)
                if (userOpt.isPresent()) {
                    User user = userOpt.get();
                    booking.setUser(user);
                }
            }
<<<<<<< HEAD

=======
            
>>>>>>> 96ed910 (Initial commit or update project)
            try {
                ensureSpecialRequirementsColumnExists();
                return bookingRepository.save(booking);
            } catch (DataAccessException e) {
                logger.warning("Error creating booking: " + e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Tour package not found with ID: " + tourPackageId);
        }
    }
<<<<<<< HEAD

    public Booking updateBookingStatus(Long id, Booking.BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);

=======
    
    public Booking updateBookingStatus(Long id, Booking.BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        
>>>>>>> 96ed910 (Initial commit or update project)
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            try {
                ensureSpecialRequirementsColumnExists();
                return bookingRepository.save(booking);
            } catch (DataAccessException e) {
                logger.warning("Error updating booking status: " + e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Booking not found with ID: " + id);
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> 96ed910 (Initial commit or update project)
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
