package net.xiaoyu233.mitemod.miteite.liar.util;

import team.unknowndomain.liar.annotation.Stealing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface NoTrans {}
