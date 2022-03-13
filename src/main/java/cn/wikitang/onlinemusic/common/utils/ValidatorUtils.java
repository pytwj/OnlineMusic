package cn.wikitang.onlinemusic.common.utils;

import cn.wikitang.onlinemusic.common.exception.ProjectCommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Package: cn.wikitang.onlinemusic.common.utils
 * @ClassName: ValidatorUtils
 * @Author: WikiTang
 * @Date: 2022/3/13 18:23
 * @Description:
 */
public class ValidatorUtils {
    private static Validator validator = ((HibernateValidatorConfiguration)((HibernateValidatorConfiguration) Validation.byProvider(HibernateValidator.class).configure()).failFast(true)).buildValidatorFactory().getValidator();

    private ValidatorUtils() {
    }

    public static void validateEntity(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        validate(constraintViolations);
    }

    public static void validateDto(Object object, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<Object> constraint = (ConstraintViolation)constraintViolations.iterator().next();
            throw new ProjectCommonException(constraint.getMessage());
        }
    }

    public static void validateProperty(Object object, String propertyName, Class<?>... groups) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validateProperty(object, propertyName, groups);
        validate(constraintViolations);
    }

    public static void validate(Set<ConstraintViolation<Object>> constraintViolations) {
        if (CollectionUtils.isNotEmpty(constraintViolations)) {
            List<String> msgList = new ArrayList(constraintViolations.size());
            Iterator var2 = constraintViolations.iterator();

            while(var2.hasNext()) {
                ConstraintViolation<Object> cv = (ConstraintViolation)var2.next();
                msgList.add(cv.getMessage());
            }

            throw new ProjectCommonException(StringUtils.join(msgList, ";"));
        }
    }

    public static void validatePropertyGroup(Object object, String... propertyName) {
        String[] var2 = propertyName;
        int var3 = propertyName.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String string = var2[var4];
            validateProperty(object, string);
        }

    }
}
