/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmContextDetailComponent } from 'app/entities/cm-context/cm-context-detail.component';
import { CmContext } from 'app/shared/model/cm-context.model';

describe('Component Tests', () => {
    describe('CmContext Management Detail Component', () => {
        let comp: CmContextDetailComponent;
        let fixture: ComponentFixture<CmContextDetailComponent>;
        const route = ({ data: of({ cmContext: new CmContext(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmContextDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CmContextDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CmContextDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.cmContext).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
