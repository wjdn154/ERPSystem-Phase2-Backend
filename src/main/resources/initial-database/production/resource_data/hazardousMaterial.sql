-- ALTER TABLE production_hazardous_material MODIFY COLUMN description VARCHAR(255);

INSERT INTO production_hazardous_material
(description, hazard_level, hazardous_material_code, hazardous_material_name)
VALUES
    ('납은 신경계에 손상을 주며, 만성 노출 시 중독 위험이 큽니다.', 'HIGH', 'HDM-001', '납'),
    ('수은은 신경 독성을 일으키며, 흡수 시 매우 위험합니다.', 'HIGH', 'HDM-002', '수은'),
    ('카드뮴은 폐암을 유발하며, 신장 손상을 일으킬 수 있습니다.', 'HIGH', 'HDM-003', '카드뮴'),
    ('니켈은 알레르기를 일으킬 수 있으며, 장기간 노출 시 암을 유발할 수 있습니다.', 'MEDIUM', 'HDM-004', '니켈'),
    ('석면은 폐암과 중피종을 유발하며, 흡입 시 매우 위험합니다.', 'HIGH', 'HDM-005', '석면'),
    ('염소화합물은 호흡기 자극과 피부 자극을 일으킬 수 있습니다.', 'MEDIUM', 'HDM-006', '염소화합물'),
    ('브롬화난연제는 환경에 축적되며, 일부는 발암 가능성이 있습니다.', 'MEDIUM', 'HDM-007', '브롬화난연제'),
    ('프탈레이트는 호르몬 교란을 일으킬 수 있는 플라스틱 가소제입니다.', 'LOW', 'HDM-008', '프탈레이트'),
    ('비소는 중독과 암을 유발할 수 있는 독성 물질입니다.', 'HIGH', 'HDM-009', '비소'),
    ('폴리염화비페닐은 발암성 물질로, 환경 오염을 일으킵니다.', 'HIGH', 'HDM-010', '폴리염화비페닐'),
    ('벤젠은 발암성 물질로, 백혈병을 유발할 수 있습니다.', 'HIGH', 'HDM-011', '벤젠'),
    ('합성물은 호흡기 자극과 신경계 손상을 일으킬 수 있습니다.', 'HIGH', 'HDM-012', '합성 화학물'),
    ('특정 수지물질은 피부 알레르기와 환경 오염을 유발할 수 있습니다.', 'MEDIUM', 'HDM-013', '합성 수지'),
    ('산화제는 폭발 위험이 높으며, 화학 반응성이 큽니다.', 'HIGH', 'HDM-014', '산화제'),

    ('중합체 가소제는 내분비 교란을 일으킬 가능성이 있습니다.', 'LOW', 'HDM-015', '중합체 가소제'),
    ('탄화수소는 장기간 노출 시 간과 신장에 손상을 줍니다.', 'HIGH', 'HDM-016', '탄화수소'),
    ('유기염소 화합물은 호르몬 교란 및 생태계에 악영향을 줄 수 있습니다.', 'MEDIUM', 'HDM-017', '유기염소 화합물'),

    ('염산은 부식성이 강하여 피부와 호흡기에 해로울 수 있습니다.', 'HIGH', 'HDM-018', '염산'),
    ('톨루엔은 중추신경계에 독성이 있으며, 흡입 시 위험합니다.', 'HIGH', 'HDM-019', '톨루엔'),
    ('디메틸 황은 호흡기 자극을 유발하며, 환경에 유독합니다.', 'MEDIUM', 'HDM-020', '디메틸 황');

-- 자재(M027)와 유해물질(HDM-001, HDM-002)을 연결
INSERT INTO material_hazardous (material_id, hazardous_material_id)
VALUES
    -- M25 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M025'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-001')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M025'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-002')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M025'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-003')),

    -- M26 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M026'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-004')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M026'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-005')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M026'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-006')),

    -- M27 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M027'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-007')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M027'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-008')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M027'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-009')),

    -- M28 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M028'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-010')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M028'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-011')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M028'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-001')),

    -- M29 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M029'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-002')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M029'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-003')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M029'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-004')),

    -- M30 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M030'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-005')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M030'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-006')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M030'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-007')),

    -- M24 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M024'),
        (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-008')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M024'),
        (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-009')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M024'),
        (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-010')),

    -- M23 자재와 유해물질 연결
    ((SELECT id FROM production_material_data WHERE material_code = 'M023'),
    (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-008')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M023'),
    (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-009')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M023'),
    (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-010')),
    ((SELECT id FROM production_material_data WHERE material_code = 'M023'),
     (SELECT id FROM production_hazardous_material WHERE hazardous_material_code = 'HDM-001'));