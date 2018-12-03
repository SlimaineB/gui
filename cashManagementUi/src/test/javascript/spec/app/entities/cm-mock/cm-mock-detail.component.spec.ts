/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmMockDetailComponent } from 'app/entities/cm-mock/cm-mock-detail.component';
import { CmMock } from 'app/shared/model/cm-mock.model';

describe('Component Tests', () => {
    describe('CmMock Management Detail Component', () => {
        let comp: CmMockDetailComponent;
        let fixture: ComponentFixture<CmMockDetailComponent>;
        const route = ({ data: of({ cmMock: new CmMock(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmMockDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CmMockDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmMockDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cmMock).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
