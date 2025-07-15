package co.istad.storeistad.base;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PagingRS {
    private int page;
    private int size;
    private int totalPage;
    private long totals;

    // PagingRS pagingRS = new PagingRS(employee.getNumber(), employee.getSize(), employee.getTotalPages(), employee.getTotalElements());
    public PagingRS(int page, int size, int totalPage, long total) {
        this.page = page;
        this.size = size;
        this.totalPage = totalPage;
        this.totals = total;
    }

    public <T> PagingRS(Page<T> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalPage = page.getTotalPages();
        this.totals = page.getTotalElements();
    }

}
