SELECT SQL_CALC_FOUND_ROWS a.id, a.modified, a.unit_id, a.assoc_unit_id, a.uri, a.multimedia_comment
  FROM
		(
			SELECT
					id,
					UNIX_TIMESTAMP(modified) AS modified,
					TRIM(EXTRACTVALUE(plugin_document_xml, '//XMLSerialisableRootElement/fields/oligoType')) AS unit_id,
					TRIM(EXTRACTVALUE(plugin_document_xml, '//XMLSerialisableRootElement/fields/DocumentField/name')) AS assoc_unit_id,
					TRIM(EXTRACTVALUE(plugin_document_xml, '//XMLSerialisableRootElement/fields/DocumentField/description')) AS uri,
					TRIM(EXTRACTVALUE(plugin_document_xml, '//XMLSerialisableRootElement/fields/meltingPoint')) AS multimedia_comment
			FROM
					annotated_document
		) AS a
 WHERE a.unit_id != ''
 