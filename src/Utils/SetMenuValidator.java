package Utils;

import Model.FeastMenu;
import Model.SetMenu;

public class SetMenuValidator extends Validation {
    public static boolean isContainSetCode(String code) {
        return !Validation.isBlank(code) && FeastMenu.isContainCode(code);
    }
}