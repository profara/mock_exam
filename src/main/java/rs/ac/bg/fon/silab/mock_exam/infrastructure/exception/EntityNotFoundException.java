package rs.ac.bg.fon.silab.mock_exam.infrastructure.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entityName,String field, Object value){
        super(entityName + " with " + field + " " + value + " doesn't exist");
    }
}
