package com.example.projectTLearn.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.projectTLearn.model.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long>, JpaSpecificationExecutor<StudentModel> {

    Set<String> ALLOWED_FIELDS = Set.of(
            "studentCode",
            "gender",
            "phone",
            "role",
            "status",
            "classId",
            "department",
            "enrollmentYear",
            "gpa");

    @Query("SELECT s FROM StudentModel s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(s.full_name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<StudentModel> searchByNameLike(@Param("name") String name);

    default List<StudentModel> searchByField(String field, Object value) {
        if (field == null || field.isBlank()) {
            throw new IllegalArgumentException("Field không được để trống");
        }

        String normalizedField = field.trim();
        if (!ALLOWED_FIELDS.contains(normalizedField)) {
            throw new IllegalArgumentException("Field không hợp lệ: " + field);
        }

        Specification<StudentModel> spec = (root, query, cb) -> cb.equal(root.get(normalizedField), value);
        return findAll(spec);
    }

    public StudentModel findStudentByStudentCode(String studentCode);
}
