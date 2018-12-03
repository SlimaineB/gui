/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmRequestDetailComponent } from 'app/entities/cm-request/cm-request-detail.component';
import { CmRequest } from 'app/shared/model/cm-request.model';

describe('Component Tests', () => {
    describe('CmRequest Management Detail Component', () => {
        let comp: CmRequestDetailComponent;
        let fixture: ComponentFixture<CmRequestDetailComponent>;
        const route = ({ data: of({ cmRequest: new CmRequest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmRequestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CmRequestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmRequestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cmRequest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
