package com.pvi.repos;

import com.pvi.domain.Brand;
import com.pvi.domain.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComputerRepo extends JpaRepository<Computer, Long> {
    List<Computer> findByBrand(Brand brand);
    List<Computer> findByCpu(String cpu);
    List<Computer> findByGpu(String gpu);
    List<Computer> findByRam(String ram);
    List<Computer> findByHdd(String hdd);
    List<Computer> findByOs(String os);

}
