   /*
    * filename: ProcException
    * Description：流程相关异常基类
    * Author：laihuihui
    * Version information
    * Date：2019/10/18
    * Copyright 2016 bydauto. All rights reserved.
    */
   package com.yhw.common.exception.proc;

   import com.yhw.common.exception.base.BaseException;

   /*
    *Classname：ProcException
    *descrption 流程相关异常基类
    *version 2019/10/18
    *author: laihuihui BYD/div5/software center
    */
   public class ProcException extends BaseException {

      private static final long serialVersionUID = 1L;

      public ProcException(String code, Object[] args) {super("proc", code, args);}
   }
