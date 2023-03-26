class Report(object):

    def __init__(self, title:str, description:str, reportUnits: list):
        self.title = title
        self.description = description
        self.report_units = reportUnits
        report_units_dict = {}
        for reportUnit in self.report_units:
            report_units_dict[reportUnit['code']] = reportUnit
        self.report_units_dict = report_units_dict

    def __str__(self) -> str:
        return super().__str__()


class ReportUnit(object):
    def __init__(self, code:str, description:str, value_str:str, type:str):
        self.code = code
        self.description = description,
        self.value_str = value_str,
        self.type = type

    def __str__(self) -> str:
        return super().__str__()


class LabsReport(object):
    def __init__(self, lab_name:str, report_units_passed:list):
        self.lab_name = lab_name
        self.report_units_passed = report_units_passed
        self.report_units_beta = []

    def __str__(self) -> str:
        return super().__str__()

    def get_csv_line_header(self):
        return [
            'WORK',
            'IS_PASSED',
            'LOC',
            'AVG_METHOD_LENGTH',
            'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_LESS_THAN_15',
            'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_15',
            'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_30',
            'PERCENTAGE_VOLUME_METHODS_WITH_LENGTH_MORE_THAN_60',
            'COUNT_METHODS_WITH_CYCL_COMPL_IN_RANGE_0_10',
            'COUNT_METHODS_WITH_CYCL_COMPL_IN_RANGE_11_999',
            'COUNT_METHODS_WITH_CYCL_COMPL_IN_RANGE_21_999',
            'COUNT_METHODS_WITH_CYCL_COMPL_IN_RANGE_41_999',
            'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_0_999',
            'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_11_999',
            'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_21_999',
            'PERCENTAGE_METHODS_WITH_CYCL_COMPL_IN_RANGE_41_999',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_0_10',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_11_999',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_21_999',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_41_999',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_61_999',
            'COUNT_METHODS_WITH_ABC_IN_RANGE_101_999',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_0_10',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_11_999',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_21_999',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_41_999',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_61_999',
            'PERCENTAGE_METHODS_WITH_ABC_IN_RANGE_101_999'
        ]

    def get_csv_lines_content(self):
        result = []
        if len(self.report_units_beta) > 0:

            result.append(self.get_csv_line_content(self.lab_name, self.report_units_beta, False))
        result.append(self.get_csv_line_content(self.lab_name, self.report_units_passed, True))
        return result

    def get_csv_line_content(self, name:str, report_units:list, is_passed:bool):
        report_units_map = { report_unit['code'] : report_unit for report_unit in report_units }
        headers_metrics = self.get_csv_line_header()[2:]
        return [name, '1' if is_passed else '0'] + [report_units_map[header]['value'] for header in headers_metrics]


