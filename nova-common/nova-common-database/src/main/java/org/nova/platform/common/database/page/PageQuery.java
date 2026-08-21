package org.nova.platform.common.database.page;


import com.mybatisflex.core.paginate.Page;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;


/**
 * Description:分页查询公共参数
 *
 * @author yyg
 * @date 2021/3/1 16:42
 */
@Getter
@Setter
public class PageQuery<P,Q>  {

    /**
     * 查询条件
     */
    @Valid
    private Q query;

    /**
     * 当前页
     */
    private  Integer   current=1 ;

    /**
     * 每页数据量
     */
    private  Integer   size =10;

    /**
     * 获取page对象
     * @return Page
     */
    public Page<P> getPage(){
        return new Page<P>(current,size);
    }

}
