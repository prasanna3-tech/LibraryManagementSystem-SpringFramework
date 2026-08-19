package org.pras.mappers.reportMappers;

import org.pras.dto.borrowDtos.BorrowedBookResponseDto;
import org.pras.models.BorrowRecord;
import org.pras.models.BorrowRecordReport;
import org.springframework.stereotype.Component;

@Component
public class BorrowRecordReportMapper {

    public BorrowRecordReport toResult(
            BorrowRecord borrowRecord) {

        return new BorrowRecordReport(

                borrowRecord.getBorrowId(),

                borrowRecord.getStudent().getStudentId(),
                borrowRecord.getStudent().getName(),

                borrowRecord.getBook().getBookId(),
                borrowRecord.getBook().getTitle(),

                borrowRecord.getIssueDate(),
                borrowRecord.getDueDate(),
                borrowRecord.getReturnDate(),

                borrowRecord.isReturned(),

                borrowRecord.getRenewCount(),

                borrowRecord.getFine(),

                borrowRecord.isFinePaid()
        );
    }
}