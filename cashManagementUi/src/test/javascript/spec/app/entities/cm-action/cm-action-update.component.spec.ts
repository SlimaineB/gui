/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmActionUpdateComponent } from 'app/entities/cm-action/cm-action-update.component';
import { CmActionService } from 'app/entities/cm-action/cm-action.service';
import { CmAction } from 'app/shared/model/cm-action.model';

describe('Component Tests', () => {
    describe('CmAction Management Update Component', () => {
        let comp: CmActionUpdateComponent;
        let fixture: ComponentFixture<CmActionUpdateComponent>;
        let service: CmActionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmActionUpdateComponent]
            })
                .overrideTemplate(CmActionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CmActionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmActionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmAction(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmAction = entity;
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
                    const entity = new CmAction();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmAction = entity;
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
