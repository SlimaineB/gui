/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmRequestUpdateComponent } from 'app/entities/cm-request/cm-request-update.component';
import { CmRequestService } from 'app/entities/cm-request/cm-request.service';
import { CmRequest } from 'app/shared/model/cm-request.model';

describe('Component Tests', () => {
    describe('CmRequest Management Update Component', () => {
        let comp: CmRequestUpdateComponent;
        let fixture: ComponentFixture<CmRequestUpdateComponent>;
        let service: CmRequestService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmRequestUpdateComponent]
            })
                .overrideTemplate(CmRequestUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CmRequestUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmRequestService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmRequest(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmRequest = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmRequest();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmRequest = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
