package com.hamroyatra.controller;

import com.hamroyatra.model.Booking;
import com.hamroyatra.model.Destination;
import com.hamroyatra.model.TourPackage;
import com.hamroyatra.model.User;
import com.hamroyatra.service.BookingService;
import com.hamroyatra.service.DestinationService;
import com.hamroyatra.service.TourPackageService;
import com.hamroyatra.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    private final UserService userService;
    private final DestinationService destinationService;
    private final TourPackageService tourPackageService;
    private final BookingService bookingService;

    @Autowired
    public AdminController(UserService userService,
                           DestinationService destinationService,
                           TourPackageService tourPackageService,
                           BookingService bookingService) {
        this.userService = userService;
        this.destinationService = destinationService;
        this.tourPackageService = tourPackageService;
        this.bookingService = bookingService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        // Get counts for dashboard
        long userCount = userService.getAllUsers().size();
        long destinationCount = destinationService.getAllDestinations().size();
        long packageCount = tourPackageService.getAllTourPackages().size();
        long bookingCount = bookingService.getAllBookings().size();

        model.addAttribute("userCount", userCount);
        model.addAttribute("destinationCount", destinationCount);
        model.addAttribute("packageCount", packageCount);
        model.addAttribute("bookingCount", bookingCount);
        model.addAttribute("pageTitle", "Admin Dashboard - HamroYatra");

        // Get recent bookings
        List<Booking> recentBookings = bookingService.getAllBookings();
        model.addAttribute("recentBookings", recentBookings);

        return "admin/index";
    }

    // Users management
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "Manage Users - HamroYatra");
        return "admin/users/list";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        Optional<User> userOpt = userService.getUserById(id);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "View User - HamroYatra");
            return "admin/users/view";
        } else {
            return "redirect:/admin/users";
        }
    }

    // Destinations management
    @GetMapping("/destinations")
    public String listDestinations(Model model) {
        List<Destination> destinations = destinationService.getAllDestinations();
        model.addAttribute("destinations", destinations);
        model.addAttribute("pageTitle", "Manage Destinations - HamroYatra");
        return "admin/destinations/list";
    }

    @GetMapping("/destinations/new")
    public String newDestinationForm(Model model) {
        model.addAttribute("destination", new Destination());
        model.addAttribute("pageTitle", "Add Destination - HamroYatra");
        return "admin/destinations/form";
    }

    @PostMapping("/destinations/save")
    public String saveDestination(@Valid @ModelAttribute("destination") Destination destination,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Add Destination - HamroYatra");
            return "admin/destinations/form";
        }

        destinationService.saveDestination(destination);
        return "redirect:/admin/destinations";
    }

    @GetMapping("/destinations/edit/{id}")
    public String editDestinationForm(@PathVariable Long id, Model model) {
        Optional<Destination> destinationOpt = destinationService.getDestinationById(id);

        if (destinationOpt.isPresent()) {
            model.addAttribute("destination", destinationOpt.get());
            model.addAttribute("pageTitle", "Edit Destination - HamroYatra");
            return "admin/destinations/form";
        } else {
            return "redirect:/admin/destinations";
        }
    }

    @GetMapping("/destinations/delete/{id}")
    public String deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return "redirect:/admin/destinations";
    }

    // Tour packages management
    @GetMapping("/packages")
    public String listPackages(Model model) {
        List<TourPackage> packages = tourPackageService.getAllTourPackages();
        model.addAttribute("packages", packages);
        model.addAttribute("pageTitle", "Manage Tour Packages - HamroYatra");
        return "admin/packages/list";
    }

    @GetMapping("/packages/new")
    public String newPackageForm(Model model) {
        model.addAttribute("tourPackage", new TourPackage());
        model.addAttribute("destinations", destinationService.getAllDestinations());
        model.addAttribute("pageTitle", "Add Tour Package - HamroYatra");
        return "admin/packages/form";
    }

    @PostMapping("/packages/save")
    public String savePackage(@Valid @ModelAttribute("tourPackage") TourPackage tourPackage,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("destinations", destinationService.getAllDestinations());
            model.addAttribute("pageTitle", "Add Tour Package - HamroYatra");
            return "admin/packages/form";
        }

        tourPackageService.saveTourPackage(tourPackage);
        return "redirect:/admin/packages";
    }

    @GetMapping("/packages/edit/{id}")
    public String editPackageForm(@PathVariable Long id, Model model) {
        Optional<TourPackage> packageOpt = tourPackageService.getTourPackageById(id);

        if (packageOpt.isPresent()) {
            model.addAttribute("tourPackage", packageOpt.get());
            model.addAttribute("destinations", destinationService.getAllDestinations());
            model.addAttribute("pageTitle", "Edit Tour Package - HamroYatra");
            return "admin/packages/form";
        } else {
            return "redirect:/admin/packages";
        }
    }

    @GetMapping("/packages/delete/{id}")
    public String deletePackage(@PathVariable Long id) {
        tourPackageService.deleteTourPackage(id);
        return "redirect:/admin/packages";
    }

    // Bookings management
    @GetMapping("/bookings")
    public String listBookings(Model model) {
        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("pageTitle", "Manage Bookings - HamroYatra");
        return "admin/bookings/list";
    }

    @GetMapping("/bookings/{id}")
    public String viewBooking(@PathVariable Long id, Model model) {
        try {
            Optional<Booking> bookingOpt = bookingService.getBookingById(id);

            if (bookingOpt.isPresent()) {
                model.addAttribute("booking", bookingOpt.get());
                model.addAttribute("pageTitle", "View Booking - HamroYatra");
                model.addAttribute("bookingStatuses", Booking.BookingStatus.values());
                return "admin/bookings/view";
            } else {
                model.addAttribute("errorMessage", "Booking not found with ID: " + id);
                return "redirect:/admin/bookings";
            }
        } catch (DataAccessException e) {
            logger.warning("Error accessing booking with ID " + id + ": " + e.getMessage());
            model.addAttribute("errorMessage", "Database error occurred. Please contact administrator.");
            return "redirect:/admin/bookings";
        }
    }

    @PostMapping("/bookings/{id}/status")
    public String updateBookingStatus(@PathVariable Long id,
                                      @RequestParam("status") Booking.BookingStatus status,
                                      Model model) {
        try {
            bookingService.updateBookingStatus(id, status);
            return "redirect:/admin/bookings/" + id;
        } catch (Exception e) {
            logger.warning("Error updating booking status: " + e.getMessage());
            model.addAttribute("errorMessage", "Error updating booking status. Please try again.");
            return "redirect:/admin/bookings/" + id;
        }
    }
}
