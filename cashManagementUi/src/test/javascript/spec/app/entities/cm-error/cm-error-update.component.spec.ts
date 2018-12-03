/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmErrorUpdateComponent } from 'app/entities/cm-error/cm-error-update.component';
import { CmErrorService } from 'app/entities/cm-error/cm-error.service';
import { CmError } from 'app/shared/model/cm-error.model';

describe('Component Tests', () => {
    describe('CmError Management Update Component', () => {
        let comp: CmErrorUpdateComponent;
        let fixture: ComponentFixture<CmErrorUpdateComponent>;
        let service: CmErrorService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmErrorUpdateComponent]
            })
                .overrideTemplate(CmErrorUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CmErrorUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmErrorService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmError(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmError = entity;
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
                    const entity = new CmError();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmError = entity;
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
