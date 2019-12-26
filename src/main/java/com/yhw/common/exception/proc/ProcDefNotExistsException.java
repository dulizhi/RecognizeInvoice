   /*
    * filename: ProcDefNotExistsException
    * Description：流程定义不存在异常
    * Author：laihuihui
    * Version information
    * Date：2019/10/18
    * Copyright 2016 bydauto. All rights reserved.
    */
   package com.yhw.common.exception.proc;

   /*
    *Classname：ProcDefNotExistsException
    *descrption 流程定义不存在异常
    *version 2019/10/18
    *author: laihuihui BYD/div5/software center
    */
   public class ProcDefNotExistsException extends ProcException {

      private static final long serialVersionUID = 1L;


      public ProcDefNotExistsException() {
         super("procDef.not.exist", null);
      }
   }
