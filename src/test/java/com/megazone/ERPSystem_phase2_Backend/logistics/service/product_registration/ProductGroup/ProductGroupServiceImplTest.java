package com.megazone.ERPSystem_phase2_Backend.logistics.service.product_registration.ProductGroup;

import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.ProductGroup;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductGroupDto;
import com.megazone.ERPSystem_phase2_Backend.logistics.repository.product_registration.ProductGroup.ProductGroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ProductGroupServiceImplTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductGroupService productGroupService;

    @Test
    @DisplayName("모든 품목 그룹 조회하기")
    void findProductGroupName() {
        // given
        ProductGroup a = new ProductGroup(1L, "01", "가공식품");
        ProductGroup b = new ProductGroup(2L, "02", "신선식품");
        ProductGroup c = new ProductGroup(3L, "03", "일상용품");
        ProductGroup d = new ProductGroup(4L, "05", "의약품/의료기기");
        ProductGroup e = new ProductGroup(5L, "06", "교육/문화용품");
        ProductGroup f = new ProductGroup(6L, "07", "디지털/가전");
        ProductGroup g = new ProductGroup(7L, "08", "가구/인테리어");
        ProductGroup h = new ProductGroup(8L, "09", "의류");
        ProductGroup i = new ProductGroup(9L, "10", "전문스포츠/레저");
        ProductGroup j = new ProductGroup(10L, "11", "패션잡화");
        ProductGroup k = new ProductGroup(11L, "99", "기타");
        List<ProductGroup> expected =
                new ArrayList<ProductGroup>(Arrays.asList(a, b, c, d, e, f, g, h, i, j, k));
        // when
        List<ProductGroup> productGroups = productGroupService.searchAllProductGroup();

        // then
        assertEquals(expected.toString(), productGroups.toString());
    }

    @Test
    void id로_폼목_그룹_조회_성공() {
        // given
        Long id = 4L;
        ProductGroup expected = new ProductGroup(id, "05", "의약품/의료기기");
        // when
        ProductGroup productGroup = productGroupService.searchByIdProductGroup(id);
        // then
        assertEquals(expected.toString(), productGroup.toString());
    }

    @Test
    void id로_폼목_그룹_조회_실패() {
        // given
        Long id = -1L;
        Throwable expected = assertThrows(RuntimeException.class, () ->
            {productGroupService.searchByIdProductGroup(id);
        });
        // when

        // then
        assertEquals("id를 찾을 수가 없습니다. " + id, expected.getMessage());
    }

    @Test
    void saveProductGroup_성공(){
        // given
        String code = "12";
        String name = "전자제품";
        ProductGroupDto dto = new ProductGroupDto(code, name);
        ProductGroup expected = new ProductGroup(12L, code, name);
        // when
        ProductGroup productGroup = productGroupService.saveProductGroup(dto);
//        entityManager.flush();
        // then
        assertEquals(expected.toString(), productGroup.toString());
    }

    /**
     * 중복된 Code의 품목 그룹 등록 시 오류 발생
     */
    @Test
    void saveProductGroup_실패(){
        // given
        String code = "99";
        String name = "전자제품";
        ProductGroupDto dto = new ProductGroupDto(code, name);
        // when
        Throwable exception = assertThrows(RuntimeException.class, () -> {
            productGroupService.saveProductGroup(dto);
        });
        // then
        assertEquals("중복된 code입니다.", exception.getMessage());
    }

}