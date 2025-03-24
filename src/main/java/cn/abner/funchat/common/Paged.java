package cn.abner.funchat.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description for this class
 *
 * <p>
 *
 * @author: Abner Song
 * <p>
 * @date: 2025/3/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paged<T> {
    public List<T> list;
    public int pageSize;
    public int pageNum;
    public long total;
    public long totalPages;
}
