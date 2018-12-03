/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmErrorDetailComponent } from 'app/entities/cm-error/cm-error-detail.component';
import { CmError } from 'app/shared/model/cm-error.model';

describe('Component Tests', () => {
    describe('CmError Management Detail Component', () => {
        let comp: CmErrorDetailComponent;
        let fixture: ComponentFixture<CmErrorDetailComponent>;
        const route = ({ data: of({ cmError: new CmError(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmErrorDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CmErrorDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmErrorDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cmError).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
