package springbootapp.movieclub.generators;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import springbootapp.movieclub.entity.MovieItem;
import springbootapp.movieclub.entity.Rental;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class InvoiceGenerators {

    public void generateInvoice(Rental rental) {
        // Generisanje fakture na osnovu podataka o najmu
        String invoiceContent = "Invoice for Rental ID: " + rental.getId() + "\n" +
                "Client: " + rental.getClient().getFirstName() +" "+rental.getClient().getLastName() + "\n" +
                "Worker: " + rental.getWorker().getFirstName() +" "+rental.getClient().getLastName() + "\n" +
                "Rental Date: " + rental.getRentalDate() + "\n" +
                "Rental Expiration: " + rental.getRentalExpiration() + "\n" +
                "Total Amount: $" + calculateTotalAmount(rental);

        // Čuvanje fakture kao PDF fajla
        String fileName = "invoice_" + rental.getId() + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();
            document.add(new Paragraph(invoiceContent));
            document.close();
            System.out.println("Invoice generated successfully: " + fileName);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
    private double calculateTotalAmount(Rental rental) {
        double totalAmount = 0.0;

        // Pristupamo listi iznajmljenih stavki i izračunavamo ukupni iznos
        List<MovieItem> movieItems = rental.getMovieItems();
        for (MovieItem movieItem : movieItems) {
            // Pretpostavimo da cena po danu za svaki predmet stoji u samom predmetu
            double pricePerDay = movieItem.getPrice();
            // Izračunavamo broj dana za koje je predmet iznajmljen
            long rentedDays = ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getRentalExpiration());
            // Dodajemo iznos za ovaj predmet na ukupni iznos
            totalAmount += pricePerDay * rentedDays;
        }

        return totalAmount;
    }

    public void generateExcel(List<Rental> rentals) {
        // Implementacija za formiranje Excel fajla na osnovu liste najmova
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rentals");

        // Kreiranje zaglavlja tabele
        String[] headers = {"Rental ID", "Client Name", "Worker Name", "Rental Date", "Rental Expiration", "Return Date"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Popunjavanje podataka o najmovima
        int rowNum = 1;
        for (Rental rental : rentals) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(rental.getId());
            row.createCell(1).setCellValue(rental.getClient().getFirstName() + " " + rental.getClient().getLastName());
            row.createCell(2).setCellValue(rental.getWorker().getFirstName() + " " + rental.getWorker().getLastName());
            row.createCell(3).setCellValue(rental.getRentalDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            row.createCell(4).setCellValue(rental.getRentalExpiration().format(DateTimeFormatter.ISO_LOCAL_DATE));
            if (rental.getReturnDate() != null) {
                row.createCell(5).setCellValue(rental.getReturnDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
            }
        }

        // Čuvanje Excel fajla na disku
        try (FileOutputStream fileOut = new FileOutputStream("rentals.xlsx")) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Zatvaranje workbook objekta
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


