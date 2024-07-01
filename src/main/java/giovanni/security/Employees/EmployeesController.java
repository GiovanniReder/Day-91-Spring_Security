package giovanni.security.Employees;

import giovanni.security.Payloads.NewEmployeesDTO;
import giovanni.security.Payloads.NewEmployeesResponseDTO;
import giovanni.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    // 1 . GET http://localhost:3001/employees
    @GetMapping
    public Page<Employees> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.employeesService.getEmployees(page, size, sortBy);
    }
    // 2 . POST http://localhost:3001/employees
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewEmployeesResponseDTO saveEmployee(@RequestBody @Validated NewEmployeesDTO body, BindingResult validationResult) throws IOException {

        if (validationResult.hasErrors()) {
            System.out.println(validationResult.getAllErrors());
            throw new BadRequestException(validationResult.getAllErrors());
        }
        return new NewEmployeesResponseDTO(this.employeesService.saveNewEmployee(body).getId());
    }

    // 3. GET http://localhost:3001/employees/{employeeId}
    @GetMapping("/{employeeId}")
    public Employees findEmployeeById(@PathVariable long employeeId) {
        return this.employeesService.findEmployeeById(employeeId);
    }

    // 4. PUT http://localhost:3001/employees/{employeeId}
    @PutMapping("/{employeeId}")
    public Employees findEmployeeByIdAndUpdate(@PathVariable long employeeId, @RequestBody Employees body) {
        return this.employeesService.findEmployeeByIdAndUpdate(employeeId , body);
    }

    // 5. DELETE http://localhost:3001/employees/{employeeId}
    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findEmployeeByIdAndDelete(@PathVariable long employeeId) {
        this.employeesService.findEmployeeByIdAndDelete(employeeId);
    }

}