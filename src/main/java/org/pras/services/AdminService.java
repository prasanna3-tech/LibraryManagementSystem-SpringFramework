package org.pras.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.pras.config.HibernateUtil;
import org.pras.exceptions.AdminNotFoundException;
import org.pras.exceptions.AdminUsernameAlreadyExistsException;
import org.pras.exceptions.InvalidAdminCredentialsException;
import org.pras.exceptions.SystemSettingsNotFoundException;
import org.pras.models.Admin;
import org.pras.models.Student;
import org.pras.models.SystemSettings;
import org.pras.repositories.AdminRepository;
import org.pras.repositories.StudentRepository;
import org.pras.repositories.SystemSettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final SystemSettingsRepository systemSettingsRepository;

    public AdminService(
            AdminRepository adminRepository,
            SystemSettingsRepository systemSettingsRepository) {

        this.adminRepository = adminRepository;
        this.systemSettingsRepository = systemSettingsRepository;
    }

    @Transactional
    public Admin addAdmin(Admin admin) {

        if (adminRepository.existsByUsername(
                admin.getUsername())) {

            throw new AdminUsernameAlreadyExistsException(
                    admin.getUsername()
            );
        }

        return adminRepository.save(admin);
    }

    public Admin loginAdmin(
            String username,
            String password) {

        return adminRepository
                .findByUsernameAndPassword(
                        username,
                        password
                )
                .orElseThrow(
                        InvalidAdminCredentialsException::new
                );
    }

    @Transactional
    public Admin removeAdmin(int adminId) {

        Admin admin =
                adminRepository.findById(adminId)
                        .orElseThrow(() ->
                                new AdminNotFoundException(adminId));

        adminRepository.delete(admin);

        return admin;
    }

    public List<Admin> getAllAdmins() {

        return adminRepository.findAll();
    }

    public void resetStudentPassword(int studentId, String newPassword) {

        Session session = HibernateUtil
                .getSessionFactory()
                .openSession();

        Transaction transaction = null;

        try {

            Student student =
                    session.find(Student.class, studentId);

            if (student == null) {

                System.out.println("Student not found");
                return;

            }

            transaction = session.beginTransaction();

            student.setPassword(newPassword);

            transaction.commit();

            System.out.println("Student password reset successfully");

        }
        catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

            }

            e.printStackTrace();

        }
        finally {

            session.close();

        }

    }

    @Transactional
    public SystemSettings updateSystemSettings(
            double finePerDay,
            int maxBooksAllowed,
            int borrowDurationDays,
            int maxRenewCount) {

        SystemSettings settings =
                systemSettingsRepository.findById(1)
                        .orElseThrow(
                                SystemSettingsNotFoundException ::new);

        settings.setFinePerDay(finePerDay);
        settings.setMaxBooksAllowed(maxBooksAllowed);
        settings.setBorrowDurationDays(borrowDurationDays);
        settings.setMaxRenewCount(maxRenewCount);

        return settings;
    }
}