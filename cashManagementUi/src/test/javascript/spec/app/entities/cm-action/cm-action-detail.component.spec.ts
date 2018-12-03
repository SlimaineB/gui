/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmActionDetailComponent } from 'app/entities/cm-action/cm-action-detail.component';
import { CmAction } from 'app/shared/model/cm-action.model';

describe('Component Tests', () => {
    describe('CmAction Management Detail Component', () => {
        let comp: CmActionDetailComponent;
        let fixture: ComponentFixture<CmActionDetailComponent>;
        const route = ({ data: of({ cmAction: new CmAction(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmActionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CmActionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmActionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cmAction).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
