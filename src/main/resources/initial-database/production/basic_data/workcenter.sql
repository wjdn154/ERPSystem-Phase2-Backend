-- 2024.10.29. updated 총 60개
INSERT INTO basic_data_workcenter (code, workcenter_type, name, description, is_active, warehouse_id, process_id) VALUES
-- 조립 공정
('ASM_FR_01', 'ASSEMBLY', '프론트 섀시 조립 작업장', '프론트 섀시 부품을 조립하는 작업장입니다.', TRUE, 2, 1),
('ASM_DR_02', 'ASSEMBLY', '도어 조립 작업장', '자동차 도어를 조립하는 작업장입니다.', TRUE, 2, 1),

-- 용접 공정
('WLD_RL_01', 'WELDING', '리어 액슬 용접 라인', '리어 액슬의 프레임을 용접하는 작업장입니다.', TRUE, 2, 2),
('WLD_FRM_02', 'WELDING', '프레임 용접 라인', '차체 프레임을 용접하는 작업장입니다.', TRUE, 2, 2),

-- 도장 공정
('PNT_BDY_01', 'PAINT', '차체 외장 도장 라인', '차체 외부를 도장하는 작업장입니다.', TRUE, 3, 3),
('PNT_INR_02', 'PAINT', '실내 도장 작업장', '차량 내부 부품을 도장하는 작업장입니다.', TRUE, 3, 3),

-- 정밀 가공 공정
('MCH_GR_01', 'MACHINING', '기어 가공 센터', '기어 부품을 정밀 가공하는 작업장입니다.', TRUE, 4, 4),
('MCH_SH_02', 'MACHINING', '샤프트 가공 라인', '샤프트를 가공하는 작업장입니다.', TRUE, 4, 4),

-- 열처리 공정
('HTT_MTL_01', 'HEAT_TREATMENT', '금속 열처리로 1호', '금속 부품을 경화하는 열처리로입니다.', TRUE, 9, 5),
('HTT_MTL_02', 'HEAT_TREATMENT', '금속 열처리로 2호', '고온 열처리 작업을 수행합니다.', TRUE, 9, 5),

-- 품질 검사 공정
('QIS_FIN_01', 'QUALITY_INSPECTION', '완제품 검사실 A', '완성된 제품을 검사합니다.', TRUE, 6, 6),
('QIS_CMP_02', 'QUALITY_INSPECTION', '부품 검사실 B', '생산된 부품의 품질을 검사합니다.', TRUE, 6, 6),


-- 프레스 공정
('PRS_PNL_01', 'PRESS', '차체 패널 프레스 라인', '차체 패널을 성형합니다.', TRUE, 12, 7),
('PRS_WHL_02', 'PRESS', '휠 프레스 작업장', '휠 부품을 프레스합니다.', TRUE, 12, 7),

-- 단조 공정
('FRG_CNK_01', 'FORGING', '크랭크축 단조 작업장', '크랭크축을 단조합니다.', TRUE, 16, 8),
('FRG_PST_02', 'FORGING', '피스톤 단조 작업장', '피스톤 부품을 단조하는 작업장입니다.', TRUE, 16, 8),

-- 플라스틱 성형 공정 (Plastic Molding) - ID: 9
('PLM_DSH01_043', 'PLASTIC_MOLDING', '대시보드 성형 작업장', '대시보드를 플라스틱 성형합니다.', TRUE, 15, 9),
('PLM_KNB02_044', 'PLASTIC_MOLDING', '플라스틱 노브 성형 작업장', '플라스틱 노브를 성형합니다.', TRUE, 15, 9),


-- 주조 공정
('CST_AL_01', 'CASTING', '알루미늄 주조 공장', '알루미늄 부품을 주조하는 작업장입니다.', TRUE, 10, 10),
('CST_IR_02', 'CASTING', '철 주조 공장', '철 부품을 주조하는 작업장입니다.', TRUE, 10, 10),

-- 세척 공정 (Cleaning) - ID: 11
('CLN_ENG01_045', 'MACHINING', '엔진 부품 세척 작업장', '엔진 부품을 세척합니다.', TRUE, 18, 11),
('CLN_FRM02_046', 'MACHINING', '프레임 세척 작업장', '프레임 부품을 세척합니다.', TRUE, 18, 11),


-- 절단 공정
('CUT_PLT_01', 'MACHINING', '판재 절단 작업장', '금속 판재를 절단하는 작업장입니다.', TRUE, 12, 12),
('CUT_TUB_02', 'MACHINING', '튜브 절단 작업장', '금속 튜브를 절단합니다.', TRUE, 12, 12),

-- 레이저 절단 공정 (Laser Cutting) - ID: 13
('LCT_PLT01_047', 'MACHINING', '판재 레이저 절단 작업장', '판재를 정밀하게 레이저로 절단합니다.', TRUE, 12, 13),
('LCT_PIP02_048', 'MACHINING', '튜브 레이저 절단 작업장', '튜브를 정밀하게 레이저로 절단합니다.', TRUE, 12, 13),


-- CNC 가공 공정
('CNC_PRP_01', 'MACHINING', '프로펠러 CNC 가공 라인', '프로펠러 부품을 정밀 가공합니다.', TRUE, 19, 14),
('CNC_BLK_02', 'MACHINING', '블록 CNC 가공 라인', '엔진 블록을 정밀 가공합니다.', TRUE, 19, 14),

-- 연마 공정 (Polishing) - ID: 15
('POL_WHL01_049', 'MACHINING', '휠 연마 작업장', '휠의 표면을 연마합니다.', TRUE, 19, 15),
('POL_BDY02_050', 'MACHINING', '차체 연마 작업장', '차체 외부를 연마합니다.', TRUE, 19, 15),


-- 조립 2차 공정
('ASM2_FR_01', 'ASSEMBLY', '프론트 범퍼 조립 라인', '프론트 범퍼 부품을 조립합니다.', TRUE, 2, 16),
('ASM2_RR_02', 'ASSEMBLY', '리어 범퍼 조립 라인', '리어 범퍼를 조립하는 작업장입니다.', TRUE, 2, 16),

-- 압출 성형 공정
('EXT_PL_01', 'PLASTIC_MOLDING', '플라스틱 창틀 성형 작업장', '플라스틱 창틀을 성형합니다.', TRUE, 15, 18),
('EXT_PIP_02', 'PLASTIC_MOLDING', '플라스틱 파이프 성형 작업장', '플라스틱 파이프를 성형합니다.', TRUE, 15, 18),

-- 압출 성형 공정 (Extrusion Molding) - ID: 18
('EXT_PIP01_051', 'PLASTIC_MOLDING', '플라스틱 파이프 압출 작업장', '플라스틱 파이프를 성형합니다.', TRUE, 15, 18),
('EXT_TBL02_052', 'PLASTIC_MOLDING', '테이블 성형 작업장', '플라스틱 테이블 부품을 성형합니다.', TRUE, 15, 18),

-- 프레스 2차 공정 (Pressing 2) - ID: 19
('PRS2_FDR01_053', 'PRESS', '도어 프레스 작업장', '도어 부품을 2차 프레스합니다.', TRUE, 12, 19),
('PRS2_PNL02_054', 'PRESS', '패널 프레스 작업장', '차체 패널을 추가 프레스합니다.', TRUE, 12, 19),

-- 경화 공정 (Hardening) - ID: 20
('HRD_BRG01_055', 'HEAT_TREATMENT', '베어링 경화 작업장', '베어링 부품을 경화합니다.', TRUE, 9, 20),
('HRD_AXL02_056', 'HEAT_TREATMENT', '액슬 경화 작업장', '액슬 부품을 경화합니다.', TRUE, 9, 20),


-- 사출 성형 공정
('INJ_PLT_01', 'PLASTIC_MOLDING', '플라스틱 패널 사출 성형', '플라스틱 패널을 성형합니다.', TRUE, 15, 21),
('INJ_BTN_02', 'PLASTIC_MOLDING', '버튼 사출 성형 작업장', '플라스틱 버튼을 성형합니다.', TRUE, 15, 21),

-- 압축 성형 공정 (Compression Molding) - ID: 22
('CMP_SEAT01_057', 'PLASTIC_MOLDING', '좌석 성형 작업장', '자동차 좌석을 압축 성형합니다.', TRUE, 15, 22),
('CMP_CVR02_058', 'PLASTIC_MOLDING', '커버 압축 성형 작업장', '커버를 성형합니다.', TRUE, 15, 22),

-- 합성 공정
('SYN_PLY_01', 'MACHINING', '폴리머 합성 작업장', '폴리머를 합성합니다.', TRUE, 4, 23),
('SYN_MET_02', 'MACHINING', '금속 합성 작업장', '금속 재료를 합성합니다.', TRUE, 4, 23),

-- 광택 공정 (Polishing) - ID: 24
('PLS_RIM01_059', 'MACHINING', '림 광택 작업장', '림을 광택 처리합니다.', TRUE, 19, 24),
('PLS_MIR02_060', 'MACHINING', '미러 광택 작업장', '사이드 미러를 광택 처리합니다.', TRUE, 19, 24),

-- 코팅 공정
('CTG_FRM_01', 'MACHINING', '프레임 코팅 작업장', '프레임에 방청 코팅을 적용합니다.', TRUE, 12, 25),
('CTG_PRT_02', 'MACHINING', '부품 코팅 작업장', '부품 표면에 코팅을 적용합니다.', TRUE, 12, 25),

-- 조립 3차 공정 (Assembly 3) - ID: 26
('ASM3_FRM01_061', 'ASSEMBLY', '프레임 조립 3차 작업장', '프레임 부품을 최종 조립합니다.', TRUE, 2, 26),
('ASM3_CVR02_062', 'ASSEMBLY', '커버 조립 3차 작업장', '커버를 최종 조립합니다.', TRUE, 2, 26),

-- 고온 처리 공정
('HTT_HOT_01', 'HEAT_TREATMENT', '고온 처리로 A', '고온에서 부품을 처리합니다.', TRUE, 9, 27),
('HTT_HOT_02', 'HEAT_TREATMENT', '고온 처리로 B', '고온에서 부품을 강화합니다.', TRUE, 9, 27),

-- 냉각 공정
('CLG_FIN_01', 'MACHINING', '완제품 냉각 작업장', '완성된 제품을 냉각합니다.', TRUE, 18, 28),
('CLG_PRM_02', 'MACHINING', '프레스 부품 냉각 작업장', '프레스 부품을 냉각합니다.', TRUE, 18, 28),

-- 플라스틱 재활용 공정 (Plastic Recycling)
('REC_CVR01_037', 'PLASTIC_MOLDING', '플라스틱 커버 재활용 작업장', '플라스틱 커버를 재활용합니다.', TRUE, 15, 29),
('REC_BTN02_038', 'PLASTIC_MOLDING', '버튼 재활용 작업장', '플라스틱 버튼을 재활용합니다.', TRUE, 15, 29),

-- 알루미늄 주조 공정 (Aluminum Casting)
('CST_BLK01_039', 'CASTING', '알루미늄 블록 주조 작업장', '알루미늄 블록을 주조합니다.', TRUE, 10, 30),
('CST_FRM02_040', 'CASTING', '프레임 주조 작업장', '프레임을 주조합니다.', TRUE, 10, 30);