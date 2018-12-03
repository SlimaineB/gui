/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CashManagementUiTestModule } from '../../../test.module';
import { CmMockUpdateComponent } from 'app/entities/cm-mock/cm-mock-update.component';
import { CmMockService } from 'app/entities/cm-mock/cm-mock.service';
import { CmMock } from 'app/shared/model/cm-mock.model';

describe('Component Tests', () => {
    describe('CmMock Management Update Component', () => {
        let comp: CmMockUpdateComponent;
        let fixture: ComponentFixture<CmMockUpdateComponent>;
        let service: CmMockService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CashManagementUiTestModule],
                declarations: [CmMockUpdateComponent]
            })
                .overrideTemplate(CmMockUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CmMockUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CmMockService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CmMock(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmMock = entity;
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
                    const entity = new CmMock();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cmMock = entity;
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
