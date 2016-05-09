<#setting locale="zh_CN">
<#assign names=[]/>



<#list aggregations['2'].buckets as b>
<#if (b['3'].buckets )??>
    <#list b['3'].buckets as m> 
     <#if !names?seq_contains(m.key)>
     	<#assign names+=[m.key]/>
     </#if>
     </#list>
     <#else>
     <#if !names?seq_contains(b.key)>
     	<#assign names+=[b.key]/>
     </#if>
     </#if>
</#list>


{
	"categories":
		[
			<#list aggregations['2'].buckets as b>
				
				"${b.key_as_string?date("yyyy-MM-dd")}"
				<#if b?has_next>,</#if>
			</#list>
			
		],
		"series":[
		
				
						{
						   "name":" ",
								"data":[
								<#list names as n>
										<#assign flag=false/>
												
												 <#list aggregations['2'].buckets as b>
												
												 		<#if n==b.key>
												 				<#if flag>,<#else><#assign flag=true/></#if>
																<#if b['1'].value?string="NaN">0<#else>${b['1'].value?string("0.00")}</#if><#if b?has_next>,</#if>
														</#if>
													
													
												</#list>
									</#list>
									]
						}
				
			
	
		]
}
