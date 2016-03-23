
Obj1 (${ obj1?size }): 
<#list obj1 as value>
 * ${ value.ObjKey }
 ${ value.ObjKey }->desc:${ value.description }
</#list>

Obj2 (${ obj2?size }): 
<#list obj2 as value>
 * ${ value.ObjKey }
</#list>

