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
		
				
							<#list names as n>
								<#assign flag=false/>
								{
										"name":"${n}",
										"data":[
										
										 <#list aggregations['2'].buckets as b>
										 	
													<#list b['3'].buckets as d>
														<#if n==d.key><#if flag>,<#else>
														<#assign flag=true/></#if>
														<#if d['1'].value?string="NaN">0<#else>${d['1'].value?string("0.00")}</#if>
														</#if>
													</#list>
											
										</#list>]
								}<#if n?has_next>,</#if>
							</#list>
				
	
		]
}
