   /*
    * filename: ProcNotAuthException
    * Description：流程操作未授权
    * Author：laihuihui
    * Version information
    * Date：2019/10/22
    * Copyright 2016 bydauto. All rights reserved.
    */
   package com.yhw.common.exception.proc;

   /*
    *Classname：ProcNotAuthException
    *descrption 流程操作未授权
    *version 2019/10/22
    *author: laihuihui BYD/div5/software center
    */
   public class ProcNotAuthException extends ProcException {

      private static final long serialVersionUID = 1L;

      public ProcNotAuthException() {
         super("proc.not.auth", null);
      }
   }
