package nl.mycompany.questionaire.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert the Java 8 new date format to the ancient stuff so that JPA knows what
 * to do with it.
 * 
 * @author kjwva_000
 *
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate date) {
		Instant instant = Instant.from(date);
		return Date.from(instant);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date value) {
		Instant instant = value.toInstant();
		return LocalDate.from(instant);
	}
}