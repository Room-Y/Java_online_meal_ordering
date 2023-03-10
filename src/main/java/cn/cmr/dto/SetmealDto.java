package cn.cmr.dto;

import cn.cmr.entity.Setmeal;
import cn.cmr.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
