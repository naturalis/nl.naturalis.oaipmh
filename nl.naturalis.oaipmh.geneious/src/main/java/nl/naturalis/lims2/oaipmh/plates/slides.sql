SELECT a.id, a.modified, a.ExtractPlaatNummerCode, a.ProjectPlaatnummerCode 
  FROM
		(
			SELECT
					id,
					UNIX_TIMESTAMP(modified) AS modified,
					TRIM(EXTRACTVALUE(document_xml, '//ExtractPlaatNummerCode')) AS ExtractPlaatNummerCode,
					TRIM(EXTRACTVALUE(document_xml, '//ProjectPlaatnummerCode')) AS ProjectPlaatnummerCode
			 FROM
					annotated_document
		) AS a
 WHERE a.ExtractPlaatNummerCode != ''