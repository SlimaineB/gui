/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmContextUpdateComponent } from 'app/entities/cm-context/cm-context-update.component';
import { CmContextService } from 'app/entities/cm-context/cm-context.service';
import { CmContext } from 'app/shared/model/cm-context.model';

describe('Component Tests', () => {
    describe('CmContext Management Update Component', () => {
        let comp: CmContextUpdateComponent;
        let fixture: ComponentFixture<CmContextUpdateComponent>;
        let service: CmContextService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmContextUpdateComponent]
            })
                .overrideTemplate(CmContextUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CmContextUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmContextService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmContext(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmContext = entity;
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
                    const entity = new CmContext();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmContext = entity;
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
