<#setting number_format="#"> 
{
    "filtered": {
        "query": {
            "query_string": {
                "query": "*",
                "analyze_wildcard": true
            }
        },
        "filter": {
            "bool": {
                "must": [
                    <#if advertAddr??>
                    {
                        "query": {
                            "match": {
                                "source_url.raw": {
                                    "query": "${advertAddr}",
                                    "type": "phrase"
                                }
                            }
                        }
                    },
                    </#if>
                    {
                        "range": {
                            "${dateRange}": {
                            	<#if startTime??>"gte": ${startTime},</#if>
                            	<#if endTime??>"lte": ${endTime},</#if>
                                "format": "epoch_millis"
                            }
                        }
                    }
                ],
                "must_not": []
            }
        }
    }
}